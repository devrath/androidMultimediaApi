package com.example.code.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.code.databinding.ActivityDownloadPartialVideoBinding
import com.example.code.vm.DownloadPartialVideoViewModel
import com.example.code.vm.SelectionActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadPartialVideoActivity : AppCompatActivity()  {

    companion object {
        const val MEDIA_URL = "mediaUrl"
    }

    private val TAG: String = DownloadPartialVideoActivity::class.java.simpleName

    private lateinit var binding: ActivityDownloadPartialVideoBinding
    private val viewModel: DownloadPartialVideoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadPartialVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromPreviousScreen()
    }


    private fun getDataFromPreviousScreen() {
        intent?.let{
            viewModel.mediaUrl = it.getStringExtra(MEDIA_URL).toString()
        }
    }

}