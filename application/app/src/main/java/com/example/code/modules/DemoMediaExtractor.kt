package com.example.code.modules

import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber

import java.io.IOException
import android.content.Context

import android.content.res.AssetFileDescriptor
import com.example.code.Constants.endPointMp4
import com.example.code.R


class DemoMediaExtractor constructor(private val context: Context) {

    private val TAG: String = DemoMediaExtractor::class.java.simpleName

    /**
     * Remote data source
     * * Setting the source as remote API
     */
    fun initiateForRemoteMp4Video() {

        try {
            val mex = MediaExtractor()
            mex.setDataSource(endPointMp4)
            val mf = mex.getTrackFormat(0)

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
            val mex = MediaExtractor()
            val afd: AssetFileDescriptor = context.resources.openRawResourceFd(R.raw.localaudio)
            mex.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)

            val mf = mex.getTrackFormat(0)

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