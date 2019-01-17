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
    private var listener = SafeListener.empty()

    fun start(listener: (GifBucket) -> Unit) {
        cancel()
        this.listener = SafeListener(listener)
        val runnable = SearchRunnable(factory, handler, this.listener)
        future = executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES)
    }

    fun cancel() {
        listener.dispose()
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
            if (time < FLY_OFF) {
                return GifBucketRequest.WaitingRequest()
            } else if (time < FIRST_LIFT) {
                return GifBucketRequest.PartyRequest()
            } else if (time < SKI_CLOSING) {
                return GifBucketRequest.SkiRequest() // TODO check hours
            } else if (time < FLY_HOME) {
                return GifBucketRequest.PartyRequest()
            }
            return GifBucketRequest.ByeRequest()
        }
    }

    private class SafeListener(private var listener: (GifBucket) -> Unit) : (GifBucket) -> Unit {
        override fun invoke(bucket: GifBucket) {
            listener.invoke(bucket)
        }

        fun dispose() {
            listener = emptyListener
        }

        companion object {
            private val emptyListener: (GifBucket) -> Unit = {}

            fun empty() = SafeListener(emptyListener)
        }
    }

    companion object {
        private const val FLY_OFF = 1548523800000L
        private const val FIRST_LIFT = 1548574200000L
        private const val SKI_CLOSING = 1549038600000L
        private const val FLY_HOME = 1549123200000L
    }
}