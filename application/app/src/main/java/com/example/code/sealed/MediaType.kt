package com.example.code.sealed

sealed class MediaType {
    object NoSelection : MediaType()
    object Mp3Selection : MediaType()
    object Mp4Selection : MediaType()
}
