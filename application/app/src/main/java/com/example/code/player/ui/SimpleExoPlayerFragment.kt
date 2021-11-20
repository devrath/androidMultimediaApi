package com.example.code.player.ui

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import android.view.*
import com.example.code.databinding.FragmentSimpleExoPlayerBinding
import com.example.code.player.core.SimpleExoplayerAction
import com.example.code.player.core.SimpleExoplayerLifecycleObserver
import com.example.code.extensions.hide
import com.example.code.extensions.show


@AndroidEntryPoint
class SimpleExoPlayerFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentSimpleExoPlayerBinding.inflate(layoutInflater)
    }

    private lateinit var locationListener: SimpleExoplayerLifecycleObserver

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExoplayerListener()
    }

    private fun initExoplayerListener() {
        activity?.let{
            locationListener = SimpleExoplayerLifecycleObserver(lifecycle,it) { exoPlayerAction ->
                when(exoPlayerAction) {
                    is SimpleExoplayerAction.BindCustomExoplayer -> binding.exoplayerView.player = exoPlayerAction.simpleExoplayer
                    is SimpleExoplayerAction.ProgressBarVisibility -> handleProgressVisibilityOfPlayer(exoPlayerAction.isVisible)
                }
            }
        }
    }

    private fun handleProgressVisibilityOfPlayer(visible: Boolean) {
        if (visible) { binding.progressBar.show() } else { binding.progressBar.hide() }
    }

}