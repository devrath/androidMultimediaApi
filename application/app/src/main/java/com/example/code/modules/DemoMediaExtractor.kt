package com.example.code.modules

import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber

import java.io.IOException
import android.content.Context

import android.content.res.AssetFileDescriptor
import android.media.MediaCodec
import android.media.MediaMuxer
import android.os.Environment
import com.example.code.Constants.endPointMp4
import com.example.code.R
import com.example.code.models.MediaObject
import java.io.File
import java.lang.Exception
import java.nio.ByteBuffer
import java.util.*


class DemoMediaExtractor(private val context: Context, val mediaObject: MediaObject) {

    private val TAG: String = DemoMediaExtractor::class.java.simpleName

    fun extractDataFromDataSource(){
        val extractor = MediaExtractor()
        extractor.setDataSource(mediaObject.url)
        TrackInfoExtractor(extractor).invoke()
        // Release the extractor
        extractor.release();
    }

    private fun selectTrack(numTracks: Int, extractor: MediaExtractor, type: String) {


        val bufferInfo = MediaCodec.BufferInfo()
        bufferInfo.presentationTimeUs = extractor.sampleTime

        // Loop the tracks and select the track required
        for (i in 0 until numTracks) {
            // Get <-MEDIA FORMAT-> from the track
            val format: MediaFormat = extractor.getTrackFormat(i)
            //  Get <-MIME TYPE-> from the format
            val mime = format.getString(MediaFormat.KEY_MIME)


            if (supportedMimeTypes(mime) && supportedMimeTrackAvailable(mime, type, extractor, i)) {
                // Get the buffer size needed
                val bufferSize = allocateBufferSize(format)
                // Set the destination buffer
                val inputBuffer = ByteBuffer.allocate(bufferSize)
                bufferInfo.size = extractor.readSampleData(inputBuffer, 0)

                while (extractor.readSampleData(inputBuffer,0)>= 0){
                    val trackIndex = extractor.sampleTrackIndex
                    val presentationTimeUs = extractor.sampleTime
                    val trackCount= extractor.trackCount
                    val trackFormat = extractor.getTrackFormat(0)
                    /**
                     * Do some thing
                     */
                   /* val outputPath = prepareOutputPath()
                    val outputFormat = MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
                    val muxer = MediaMuxer(outputPath, outputFormat)

                    val extractorToMuxerTrackIndexMap: HashMap<Int, Int> =
                        addTracksToMuxerAndGetIndexMap(trackCount,trackFormat, muxer)

                    copySamplesToMuxer(
                        muxer,extractorToMuxerTrackIndexMap,
                        trackIndex,inputBuffer,bufferInfo
                    )
                    */
                    Timber.tag(TAG).d("trackIndex:$trackIndex-- --presentationTimeUs:$presentationTimeUs")
                    extractor.advance();
                }
            }else{
                Timber.tag(TAG).d("Supported mime type is not available !")
            }
        }
    }

    private fun supportedMimeTrackAvailable(mime: String?, type: String,
                                            extractor: MediaExtractor, i: Int): Boolean {
        return if (mime == type) { extractor.selectTrack(i)
            true
        } else { false }
    }

    private fun supportedMimeTypes(mime: String?): Boolean {
        // Formats we are supporting
        val audioPrefix = "audio/"
        val videoPrefix = "video/"
        return mime?.startsWith(audioPrefix) == true || mime?.startsWith(videoPrefix) == true
    }

    private fun allocateBufferSize(format: MediaFormat): Int {
        var inputBufferSize = -1
        // Get the form
        if (format.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) {
            inputBufferSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
        }
        return inputBufferSize
    }

    private fun copySamplesToMuxer(
        muxer: MediaMuxer,
        extractorToMuxerTrackIndexMap: HashMap<Int, Int>,
        trackIndex: Int,
        inputBuffer: ByteBuffer,
        bufferInfo: MediaCodec.BufferInfo
    ) {

        try {

            muxer.start()

            var shouldProcess = true

            while (shouldProcess) {
                extractorToMuxerTrackIndexMap[trackIndex]?.let { trackIndexInMuxer ->
                    muxer.writeSampleData(trackIndexInMuxer, inputBuffer, bufferInfo)
                }
            }

            muxer.stop()

        }catch (ex: Exception){
            val errorMsg = ex.message
            Timber.tag(TAG).e(errorMsg)
            ex.printStackTrace()
        }

    }

    private fun addTracksToMuxerAndGetIndexMap(
        trackCount: Int, format: MediaFormat, muxer: MediaMuxer
    ): HashMap<Int, Int> {
        val extractorToMuxerTrackIndexMap: HashMap<Int, Int> = HashMap(trackCount)
        for (i in 0 until trackCount) {
            extractorToMuxerTrackIndexMap[i] = muxer.addTrack(format)
        }
        return extractorToMuxerTrackIndexMap
    }

    private fun prepareOutputPath() = File(
        getAppExternalVideoSnippetsStoragePath(context),
        UUID.randomUUID().toString() + ".mp4"
    ).absolutePath

    private fun getAppExternalVideoSnippetsStoragePath(context: Context): File {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            "VideoSnippets")
        if (!file.exists()) file.mkdir()
        return file
    }

    /**
     * Remote data source
     * * Setting the source as remote API
     */
    fun initiateForRemoteMp4Video() {
        try {
            val extractor = MediaExtractor()
            extractor.setDataSource(endPointMp4)
            val mf = extractor.getTrackFormat(0)

            val bitRate = mf.getInteger(MediaFormat.KEY_BIT_RATE)
            val sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            val mime = mf.getString(MediaFormat.KEY_MIME)
            val channelCount = mf.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
            val trackId = mf.getInteger(MediaFormat.KEY_TRACK_ID)

            Timber.tag(TAG).d("Bit Rate: ---------> $bitRate");
            Timber.tag(TAG).d("Sample Rate: ------> $sampleRate");
            Timber.tag(TAG).d("Mime: -------------> $mime");
            Timber.tag(TAG).d("Channel Count: ----> $channelCount");
            Timber.tag(TAG).d("Track ID: ---------> $trackId");

        } catch (e: IOException) {
            e.printStackTrace()
            Timber.tag(TAG).e("Error: ---------> ${e.message}");
        }

    }

    /**
     * Local asset data source
     * * Assuming a raw resource located at "res/raw/localaudio.mp3"
     */
    fun initiateForLocalMp3Video() {
        try {
            val extractor = MediaExtractor()
            val afd: AssetFileDescriptor = context.resources.openRawResourceFd(R.raw.localaudio)
            extractor.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)

            val mf = extractor.getTrackFormat(0)

            val bitRate = mf.getInteger(MediaFormat.KEY_BIT_RATE)
            val sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            val mime = mf.getString(MediaFormat.KEY_MIME)
            val channelCount = mf.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
            val trackId = mf.getInteger(MediaFormat.KEY_TRACK_ID)

            Timber.tag(TAG).d("Bit Rate: ---------> $bitRate");
            Timber.tag(TAG).d("Sample Rate: ------> $sampleRate");
            Timber.tag(TAG).d("Mime: -------------> $mime");
            Timber.tag(TAG).d("Channel Count: ----> $channelCount");
            Timber.tag(TAG).d("Track ID: ---------> $trackId");
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}