package com.example.code.modules

import android.media.MediaExtractor
import android.media.MediaFormat
import com.example.code.models.BasicTrackInfo
import com.example.code.models.TrackParams
import com.google.gson.Gson
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class TrackInfoExtractor @Inject constructor(
    private val gson : Gson
) {

    private val TAG: String = TrackInfoExtractor::class.java.simpleName
    private val extractor: MediaExtractor = MediaExtractor()

    companion object {
        // -----------------> Track properties
        const val ID = "track-id"
        const val MIME = "mime"
        const val LANGUAGE = "language"
        const val DISPLAY_WIDTH = "display-width"
        const val DISPLAY_HEIGHT = "display-height"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val DURATION = "durationUs"
        const val MAX_INPUT_SIZE = "max-input-size"
        const val FRAME_RATE = "frame-rate"
        // -----------------> Track type
        const val AUDIO_PREFIX = "audio/"
        const val VIDEO_PREFIX = "video/"
        const val UND = "und"
    }

    operator fun invoke(url: String) {
        // Print Track-Information
        try {
            extractor.setDataSource(url)
            printTrackLog()
        }catch (ex:Exception){
            Timber.tag(TAG).d("Error::-> ${ex.message}")
        }
    }

    fun release() {
        extractor.release()
    }

    /**
     * Print the track information
     */
    private fun printTrackLog() {
        val numTracks: Int = extractor.trackCount
        Timber.tag(TAG).d("Number of tracks::-> $numTracks")
        if(numTracks>0){
            for(i in 0 until numTracks){
                // Get <-MEDIA FORMAT-> from the track
                val format: MediaFormat = extractor.getTrackFormat(i)
                Timber.tag(TAG).d("<------------------------------------------------>")
                //Timber.tag(TAG).d("Media Format for track at index-$i is $format")
                if(format.containsKey(ID)){
                    Timber.tag(TAG).d("TrackID::-> ${format.getInteger(ID)}")
                }
                if(format.containsKey(MIME)){
                    Timber.tag(TAG).d("MimeType::-> ${format.getString(MIME)}")
                }
                if(format.containsKey(LANGUAGE)){
                    Timber.tag(TAG).d("Language::-> ${format.getString(LANGUAGE)}")
                }
                if(format.containsKey(DISPLAY_WIDTH)){
                    Timber.tag(TAG).d("DisplayWidth::-> ${format.getInteger(DISPLAY_WIDTH)}")
                }
                if(format.containsKey(DISPLAY_HEIGHT)){
                    Timber.tag(TAG).d("DisplayHeight::-> ${format.getInteger(DISPLAY_HEIGHT)}")
                }
                if(format.containsKey(WIDTH)){
                    Timber.tag(TAG).d("Width::-> ${format.getInteger(WIDTH)}")
                }
                if(format.containsKey(HEIGHT)){
                    Timber.tag(TAG).d("Height::-> ${format.getInteger(HEIGHT)}")
                }
                if(format.containsKey(DURATION)){
                    Timber.tag(TAG).d("Duration::-> ${format.getLong(DURATION)}")
                }
                if(format.containsKey(MAX_INPUT_SIZE)){
                    Timber.tag(TAG).d("MaxInputSize::-> ${format.getInteger(MAX_INPUT_SIZE)}")
                }
                if(format.containsKey(FRAME_RATE)){
                    Timber.tag(TAG).d("FrameRate::-> ${format.getInteger(FRAME_RATE)}")
                }
                Timber.tag(TAG).d("<------------------------------------------------>")
            }
        }else{
            Timber.tag(TAG).d("There are no tracks available")
        }
    }


    /**
     * Checking If the audio and Video tracks are available
     * Checking how many audio and video tracks are present
     */
    fun prepTrackInfo(): BasicTrackInfo {
        var audioTracks = 0
        var videoTracks = 0

        val trackParams:  ArrayList<TrackParams> = arrayListOf()

        val numTracks: Int = extractor.trackCount

        if(numTracks>0){
            for(i in 0 until numTracks){
                val format: MediaFormat = extractor.getTrackFormat(i)
                var languageDefined = false
                var languageName = ""
                var audioPresent = false
                var videoPresent = false
                var mime = ""

                if(format.containsKey(MIME)){
                    format.getString(MIME)?.let { currentMime ->
                        mime = currentMime
                        when {
                            currentMime.startsWith(AUDIO_PREFIX) -> {
                                audioPresent = true
                                audioTracks += 1

                                if(format.containsKey(LANGUAGE)){
                                    format.getString(LANGUAGE)?.let {
                                        if(it == UND){
                                            languageDefined = false
                                            languageName = ""
                                        }else{
                                            languageDefined = true
                                            languageName = it
                                        }
                                    }
                                }
                            }
                            currentMime.startsWith(VIDEO_PREFIX) -> {
                                videoPresent = true
                                videoTracks += 1

                                if(format.containsKey(LANGUAGE)){
                                    format.getString(LANGUAGE)?.let {
                                        if(it == UND){
                                            languageDefined = false
                                            languageName = ""
                                        }else{
                                            languageDefined = true
                                            languageName = it
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                val trackParam = TrackParams(
                    trackNo = i, trackLanguageDefined = languageDefined,
                    trackLanguage = languageName, isAudioPresent = audioPresent,
                    isVideoPresent = videoPresent, mime = mime
                )

                trackParams.add(trackParam)
            }
        }else{
            Timber.tag(TAG).d("There are no tracks available")
        }

        val trackBuilder = BasicTrackInfo(
            noOfAudioTracks = audioTracks, noOfVideoTracks = videoTracks,trackParams = trackParams
        )

        Timber.tag(TAG).d("Track builder Info:-> ".plus(gson.toJson(trackBuilder)))

        return trackBuilder
    }

}