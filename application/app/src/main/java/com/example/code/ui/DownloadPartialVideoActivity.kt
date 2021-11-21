package com.example.code.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.code.databinding.ActivityDownloadPartialVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadPartialVideoActivity : AppCompatActivity()  {

    companion object {
        const val MEDIA_URL = "mediaUrl"
    }

    private val TAG: String = DownloadPartialVideoActivity::class.java.simpleName

    private lateinit var binding: ActivityDownloadPartialVideoBinding

    private var mediaUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadPartialVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromPreviousScreen()
    }


    private fun getDataFromPreviousScreen() {
        intent?.let{
            mediaUrl= it.getStringExtra(MEDIA_URL).toString()
        }
    }

}