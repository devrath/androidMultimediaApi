package com.example.code.application

import android.app.Application
import com.example.code.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree


class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }

}