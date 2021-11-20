package com.example.code.modules

import android.media.MediaExtractor

import android.content.Context

import com.example.code.models.MediaObject
import com.google.gson.Gson


class DemoMediaExtractor(
    private val context: Context,
    private val mediaObject: MediaObject,
    private val gson : Gson
) {

    private val TAG: String = DemoMediaExtractor::class.java.simpleName

    fun extractDataFromDataSource(){
        val extractor = MediaExtractor()
        extractor.setDataSource(mediaObject.url)
        //TrackInfoExtractor(extractor,gson).invoke()
        TrackInfoExtractor(extractor,gson).prepTrackInfo()
        // Release the extractor
        extractor.release();
    }
}