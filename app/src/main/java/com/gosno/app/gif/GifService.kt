package com.gosno.app.gif

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class GifService {
    private val handler = Handler(Looper.getMainLooper())
    private val factory = GifBucketFactory()
    private val executorService =
        Executors.newSingleThreadScheduledExecutor { runnable -> Thread(runnable, "GifScheduler") }
    private var future: Future<*>? = null

    fun start(listener: (GifBucket) -> Unit) {
        cancel()
        future = executorService.scheduleAtFixedRate(SearchRunnable(factory, handler, listener), 0, 1, TimeUnit.MINUTES)
    }

    fun cancel() {
        future?.cancel(true)
    }

    private class SearchRunnable(
        private val bucketFactory: GifBucketFactory,
        private val handler: Handler,
        private val listener: (GifBucket) -> Unit
    ) : Runnable {
        private var lastRequest: GifBucketRequest? = null

        override fun run() {
            val request = getRequest(System.currentTimeMillis())
            if (request != lastRequest) {
                val bucket = bucketFactory.provide(request)
                handler.post { listener(bucket) }
                lastRequest = request
            }
        }

        private fun getRequest(time: Long): GifBucketRequest {
            // TODO
            return GifBucketRequest.ByeRequest()
        }
    }

    companion object {
        private const val FLY_OFF = 1L
        private const val BARCELONA = 2L
        private const val SKY_TIME = 3L
        private const val BARCELONA_2 = 4L
        private const val FLY_HOME = 5L
    }
}