package com.example.code.di

import android.media.MediaExtractor
import com.example.code.modules.TrackInfoExtractor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaModules {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideMediaExtractor(): MediaExtractor {
        return MediaExtractor()
    }

    @Provides
    fun provideTrackInfoExtractorModule(gson : Gson): TrackInfoExtractor {
        return TrackInfoExtractor(gson)
    }


}