package com.gosno.app

import android.app.Application
import android.content.Context
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.gosno.app.onionrepository.OnionRepository

class BaseApplication : Application() {
    private lateinit var onionRepository: OnionRepository

    override fun onCreate() {
        super.onCreate()
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
        initOnionRepository()
    }

    private fun initOnionRepository() {
        onionRepository = OnionRepository()
        onionRepository.init(applicationContext)
    }

    companion object {
        fun isOnion(context: Context) = (context.applicationContext as BaseApplication).onionRepository.isOnion()
    }
}