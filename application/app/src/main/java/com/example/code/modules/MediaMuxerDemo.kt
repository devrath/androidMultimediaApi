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
            //printTrackLog()
        }catch (ex: Exception){
            Timber.tag(TAG).d("Error::-> ${ex.message}")
        }
    }



    fun release() {
        extractor.release()
    }

}