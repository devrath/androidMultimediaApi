package com.example.code.models

data class TrackParams(
        val trackNo:Int=0,
        val trackLanguageDefined:Boolean=false,
        val trackLanguage:String="",
        val isAudioPresent:Boolean=false,
        val isVideoPresent:Boolean=false,
        val mime : String = ""
    )
