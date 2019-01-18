package com.gosno.app

import android.app.Application
import android.content.Context
import com.dvoiss.geocities.Geocities
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.gosno.app.component.onionrepository.OnionRepository

class BaseApplication : Application() {
    private lateinit var onionRepository: OnionRepository

    override fun onCreate() {
        super.onCreate()
        Geocities.init(this)
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
        initOnionRepository()
    }

    private fun initOnionRepository() {
        onionRepository = OnionRepository()
        onionRepository.init(applicationContext)
    }

    companion object {
        fun isOnion(context: Context) = (context.applicationContext as BaseApplication).onionRepository.isOnion()

        fun setIsOnion(context: Context, enable: Boolean) =
            (context.applicationContext as BaseApplication).onionRepository.setIsOnion(enable)
    }
}

fun Context.isOnion() = BaseApplication.isOnion(this)

fun Context.setIsOnion(enable: Boolean) = BaseApplication.setIsOnion(this, enable)