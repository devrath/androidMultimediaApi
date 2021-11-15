package com.example.code

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaExtractor
import android.media.MediaFormat
import android.os.Environment
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*

class Utils {

    private val TAG: String = Utils::class.java.simpleName


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

    private fun prepareOutputPath(context:Context) = File(
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
            extractor.setDataSource(Constants.endPointMp4)
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
    fun initiateForLocalMp3Video(context:Context) {
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
