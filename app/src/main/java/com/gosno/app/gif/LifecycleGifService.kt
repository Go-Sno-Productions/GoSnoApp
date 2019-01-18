package com.gosno.app.gif

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.gosno.app.R

class LifecycleGifService(private val listener: (gifRes: Int) -> Unit) {
    private val service = GifService()
    private var gifRotator: GifRotator? = null

    fun attach(lifecycle: Lifecycle) {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                service.start { bucket ->
                    val rotator = GifRotator(bucket)
                    gifRotator = rotator
                    listener(rotator.nextId())
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                service.cancel()
            }
        })
    }

    fun requestNewGif() = gifRotator?.nextId() ?: DEFAULT_IMAGE

    companion object {
        private const val DEFAULT_IMAGE = R.mipmap.ic_launcher
    }
}