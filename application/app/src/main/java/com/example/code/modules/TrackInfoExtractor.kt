package com.example.code.modules

import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber
import java.lang.Exception

class TrackInfoExtractor(private val extractor: MediaExtractor) {

    private val TAG: String = TrackInfoExtractor::class.java.simpleName

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
    }

    operator fun invoke() {
        // Print Track-Information
        try {
            printTrackLog()
        }catch (ex:Exception){
            Timber.tag(TAG).d("Error::-> ${ex.message}")
        }
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
        }
    }

}