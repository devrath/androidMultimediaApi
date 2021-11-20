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
}