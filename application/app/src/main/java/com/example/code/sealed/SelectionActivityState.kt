package com.example.code.sealed

import com.example.code.models.MediaObject

sealed class SelectionActivityState {
    data class StartPartialDownloadFeature(val mediaObject: MediaObject) : SelectionActivityState()
}

