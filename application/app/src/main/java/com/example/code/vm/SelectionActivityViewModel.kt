package com.example.code.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.code.Constants.endPointMp3
import com.example.code.Constants.endPointMp4
import com.example.code.Constants.mimeTypeMp3
import com.example.code.Constants.mimeTypeMp4
import com.example.code.models.MediaObject
import com.example.code.modules.MediaMuxerDemo
import com.example.code.modules.TrackInfoExtractor
import com.example.code.sealed.MediaType
import com.example.code.sealed.SelectionActivityState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionActivityViewModel @Inject constructor(
    private val gson : Gson
) : ViewModel(){

    private val _progressVisibility = MutableSharedFlow<Boolean>()
    val progressVisibility = _progressVisibility.asSharedFlow()

    private val _selectionActivityUiState = MutableSharedFlow<SelectionActivityState>()
    val selectionActivityUiState = _selectionActivityUiState.asSharedFlow()

    private var mediaList : ArrayList<MediaObject> = arrayListOf(
        // <Position 0> === MP3
        MediaObject(url = endPointMp3,mediaMime = mimeTypeMp3),
        // <Position 1> === MP4
        MediaObject(url = endPointMp4,mediaMime = mimeTypeMp4)
    )

    var selection : MediaType = MediaType.NoSelection

    fun setInitialSelection(currentSelection : MediaType) {
        selection = currentSelection
    }

    private fun getMediaObject(): MediaObject {
        return when (selection) {
            MediaType.Mp3Selection -> mediaList[0]
            MediaType.Mp4Selection -> mediaList[1]
            else -> mediaList[1]
        }
    }


    /************** Modules **************/
    fun mediaExtractor() {
        TrackInfoExtractor(gson).apply {
            progressVisibility(isVisible = true)
            invoke(getMediaObject().url)
            release()
            progressVisibility(isVisible = false)
        }
    }

    fun mediaMuxer() {
        MediaMuxerDemo().apply {
            progressVisibility(isVisible = true)
            invoke(getMediaObject().url)
            release()
            progressVisibility(isVisible = false)
        }
    }
    /************** Modules **************/


    private fun progressVisibility(isVisible:Boolean) {
        viewModelScope.launch {
            _progressVisibility.emit(isVisible)
        }
    }

    fun partialVideoDownload() {
        viewModelScope.launch {
            _selectionActivityUiState.emit(SelectionActivityState.StartPartialDownloadFeature(getMediaObject()))
        }
    }

}