package com.example.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.code.R
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

        binding.apply {
            chipGroupContainerId.mediaGroupId.setOnCheckedChangeListener { group, checkedId ->
                var selection = ""
                when (checkedId) {
                    R.id.chipDash -> {
                        selection = "DASH-SELECTION"
                    }
                    R.id.chipHls -> {
                        selection = "HLS-SELECTION"
                    }
                    R.id.chipMp3 -> {
                        selection = "MP3-SELECTION"
                    }
                    R.id.chipMp4 -> {
                        selection = "MP4-SELECTION"
                    }
                }
                Toast.makeText(this@SelectionActivity,selection,Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun mediaExtractor() {
        DemoMediaExtractor(this).extractDataFromDataSource()
    }


}