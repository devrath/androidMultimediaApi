package com.example.code.vm

import androidx.lifecycle.ViewModel
import com.example.code.Constants.endPointMp3
import com.example.code.Constants.endPointMp4
import com.example.code.Constants.mimeTypeMp3
import com.example.code.Constants.mimeTypeMp4
import com.example.code.models.MediaObject
import com.example.code.sealed.MediaType

class SelectionActivityViewModel : ViewModel(){

    private var mediaList : ArrayList<MediaObject> = arrayListOf(
        // <Position 0> === MP3
        MediaObject(url = endPointMp3,mediaMime = mimeTypeMp3),
        // <Position 1> === MP4
        MediaObject(url = endPointMp4,mediaMime = mimeTypeMp4)
    )

    var selection : MediaType = MediaType.NoSelection
    var isInitialSelectionMade : Boolean = false


    fun getMedia(position:Int) : MediaObject {
        return mediaList[position]
    }

    fun setInitialSelection(currentSelection : MediaType) {
        selection = currentSelection
        isInitialSelectionMade = true
    }


    fun validateIsMediaSelected(): Boolean {
        return isInitialSelectionMade
    }

    fun getMediaObject(): MediaObject {
        return when (selection) {
            MediaType.Mp3Selection -> mediaList[0]
            MediaType.Mp4Selection -> mediaList[1]
            else -> mediaList[1]
        }
    }

}