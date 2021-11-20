package com.example.code.player.core

import com.google.android.exoplayer2.SimpleExoPlayer

sealed class SimpleExoplayerAction {
    data class  BindCustomExoplayer(val simpleExoplayer: SimpleExoPlayer) : SimpleExoplayerAction()
    data class  ProgressBarVisibility(val isVisible: Boolean) : SimpleExoplayerAction()
}