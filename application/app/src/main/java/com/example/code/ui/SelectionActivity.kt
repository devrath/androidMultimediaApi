package com.example.code.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.code.databinding.ActivitySelectionBinding
import com.example.code.sealed.MediaType
import com.example.code.vm.SelectionActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import com.example.code.R
import com.example.code.models.MediaObject
import com.example.code.sealed.SelectionActivityState
import timber.log.Timber


@AndroidEntryPoint
class SelectionActivity : AppCompatActivity() {

    private val TAG: String = SelectionActivity::class.java.simpleName

    private lateinit var binding: ActivitySelectionBinding

    private val viewModel: SelectionActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        subscribeData()
    }

    private fun setOnClickListeners() {

        binding.apply {

            mediaExtractorId.setOnClickListener {
                viewModel.mediaExtractor()
            }

            mediaMuxerId.setOnClickListener {
                viewModel.mediaMuxer()
            }

            downloadPartialVideoId.setOnClickListener {
                viewModel.partialVideoDownload()
            }

            chipGroupContainerId.mediaGroupId.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.chipMp3 -> viewModel.setInitialSelection(MediaType.Mp3Selection)
                    R.id.chipMp4 -> viewModel.setInitialSelection(MediaType.Mp4Selection)
                }
            }
        }
    }

    private fun subscribeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.progressVisibility.collect {
                when {
                    it ->  Timber.tag(TAG).d("Progress Shown")
                    else -> Timber.tag(TAG).d("Progress dismissed")
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.selectionActivityUiState.collect {
                when(it){
                    is SelectionActivityState.StartPartialDownloadFeature -> startPartialDownloadFeature(it.mediaObject)
                }
            }
        }

    }

    private fun startPartialDownloadFeature(mediaObject: MediaObject) {
        val intent = Intent(this@SelectionActivity, DownloadPartialVideoActivity::class.java)
        intent.putExtra(DownloadPartialVideoActivity.MEDIA_URL,mediaObject.url)
        startActivity(intent)
    }

}