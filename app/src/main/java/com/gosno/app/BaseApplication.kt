package com.gosno.app

import android.app.Application
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
    }
}