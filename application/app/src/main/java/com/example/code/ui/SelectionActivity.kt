package com.example.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.code.databinding.ActivitySelectionBinding
import com.example.code.modules.DemoMediaExtractor

class SelectionActivity : AppCompatActivity() {

    private val TAG: String = SelectionActivity::class.java.simpleName

    private lateinit var binding: ActivitySelectionBinding

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
        }
    }

    private fun mediaExtractor() {
        DemoMediaExtractor(this).extractDataFromDataSource()
    }


}