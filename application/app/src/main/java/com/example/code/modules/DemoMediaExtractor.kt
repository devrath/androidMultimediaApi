package com.example.code.modules

import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber

import java.io.IOException
import android.content.Context

import android.content.res.AssetFileDescriptor
import android.media.MediaFormat.MIMETYPE_VIDEO_AVC
import com.example.code.Constants.endPointMp4
import com.example.code.R
import java.nio.ByteBuffer


class DemoMediaExtractor constructor(private val context: Context) {

    private val TAG: String = DemoMediaExtractor::class.java.simpleName

    fun extractDataFromDataSource(){
        val extractor = MediaExtractor()
        extractor.setDataSource(endPointMp4)
        val numTracks: Int = extractor.trackCount
        // Select the track that we need
        selectTrack(numTracks, extractor,MIMETYPE_VIDEO_AVC)
        // Release the extractor
        extractor.release();
    }


    private fun selectTrack(numTracks: Int, extractor: MediaExtractor, type: String) {

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

                while (extractor.readSampleData(inputBuffer,0)>= 0){
                    val trackIndex = extractor.sampleTrackIndex
                    val presentationTimeUs = extractor.sampleTime
                    /**
                     * Do some thing
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