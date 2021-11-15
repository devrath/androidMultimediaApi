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


}