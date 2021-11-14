package com.example.code

import android.media.MediaFormat.*

object Constants {
    const val endPointMp3 = "https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3"
    const val endPointMp4 = "https://i.imgur.com/7bMqysJ.mp4"
    const val endPointDash = "https://www.youtube.com/api/manifest/dash/id/bf5bb2419360daf1/source/youtube?as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&ipbits=0&expire=19000000000&signature=51AF5F39AB0CEC3E5497CD9C900EBFEAECCCB5C7.8506521BFC350652163895D4C26DEE124209AA9E&key=ik0"
    const val endPointHls = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8"

    const val mimeTypeMp3 = MIMETYPE_AUDIO_MPEG
    const val mimeTypeMp4 = MIMETYPE_VIDEO_AVC
    //const val mimeTypeDash = "111"
    //const val mimeTypeHls = "111"

}