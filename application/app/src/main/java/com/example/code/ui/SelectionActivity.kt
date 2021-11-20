package com.example.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.code.Constants
import com.example.code.R
import com.example.code.databinding.ActivitySelectionBinding
import com.example.code.models.MediaObject
import com.example.code.modules.DemoMediaExtractor
import com.example.code.sealed.MediaType
import com.example.code.vm.SelectionActivityViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun setOnClickListeners() {

        binding.apply {

            mediaExtractorId.setOnClickListener {
                mediaExtractor()
            }

            chipGroupContainerId.mediaGroupId.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.chipMp3 -> viewModel.setInitialSelection(MediaType.Mp3Selection)
                    R.id.chipMp4 -> viewModel.setInitialSelection(MediaType.Mp4Selection)
                }
            }

        }
    }

    private fun mediaExtractor() {
        // val mediaObject = MediaObject(url = Constants.endPointMp4,mediaMime = Constants.mimeTypeMp4)
        // val mediaObject =  MediaObject(url = Constants.endPointMp3,mediaMime = Constants.mimeTypeMp3)
        DemoMediaExtractor(this,viewModel.getMediaObject(), Gson()).extractDataFromDataSource()
    }


}