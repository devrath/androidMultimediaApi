package com.example.code.modules

import android.media.MediaExtractor
import timber.log.Timber
import java.lang.Exception

class MediaMuxerDemo {

    private val TAG: String = MediaMuxerDemo::class.java.simpleName
    private val extractor: MediaExtractor = MediaExtractor()


    operator fun invoke(url: String) {
        // Print Track-Information
        try {
            extractor.setDataSource(url)
            initiate()
        }catch (ex: Exception){
            Timber.tag(TAG).d("Error::-> ${ex.message}")
        }
    }

    fun release() {
        extractor.release()
    }

    private fun initiate() {
        val numTracks: Int = extractor.trackCount
        Timber.tag(TAG).d("Number of tracks::-> $numTracks")
        if(numTracks>0){
            for(i in 0 until numTracks){

            }
        }else{
            Timber.tag(TAG).d("There are no tracks available")
        }
    }

}