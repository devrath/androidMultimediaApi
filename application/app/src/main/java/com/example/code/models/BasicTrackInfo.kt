package com.example.code.models

data class BasicTrackInfo(
    var noOfAudioTracks:Int = 0,
    var noOfVideoTracks:Int = 0,
    var trackParams: ArrayList<TrackParams> = arrayListOf()
)
