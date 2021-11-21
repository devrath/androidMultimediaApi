package com.example.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.code.databinding.ActivitySimpleExoPlayerBinding
import com.example.code.extensions.hide
import com.example.code.extensions.show
import com.example.code.player.SimpleExoplayerAction
import com.example.code.player.SimpleExoplayerLifecycleObserver
import com.google.android.exoplayer2.util.MimeTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleExoPlayerActivity : AppCompatActivity() {

    companion object{
        const val URL = "url"
        const val TYPE = "type"
    }

    private var url = "https://i.imgur.com/7bMqysJ.mp4"
    private var type = MimeTypes.APPLICATION_MP4

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivitySimpleExoPlayerBinding.inflate(layoutInflater)
    }

    private lateinit var locationListener: SimpleExoplayerLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //getDataFromPreviousScreen()
        initExoplayerListener()
    }

    private fun initExoplayerListener() {
        locationListener = SimpleExoplayerLifecycleObserver(
            lifecycle,
            this,
        ) { exoPlayerAction ->
            when(exoPlayerAction) {
                is SimpleExoplayerAction.BindCustomExoplayer -> binding.exoplayerView.player = exoPlayerAction.simpleExoplayer
                is SimpleExoplayerAction.ProgressBarVisibility -> handleProgressVisibilityOfPlayer(exoPlayerAction.isVisible)
            }
        }
    }

    private fun handleProgressVisibilityOfPlayer(visible: Boolean) {
        if (visible) { binding.progressBar.show() } else { binding.progressBar.hide() }
    }


    private fun getDataFromPreviousScreen() {
        intent?.let{
            url= it.getStringExtra(URL).toString()
            type= it.getStringExtra(TYPE).toString()
        }
    }


}