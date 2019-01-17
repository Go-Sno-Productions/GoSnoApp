package com.gosno.app.gif

import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class GifService {
    private val handler = Handler(Looper.getMainLooper())
    private val factory = GifBucketFactory()
    private val calendar = Calendar.getInstance().apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val executorService = Executors.newSingleThreadScheduledExecutor { run -> Thread(run, "GifScheduler") }
    private var future: Future<*>? = null
    private var listener = SafeListener.empty()

    fun start(listener: (GifBucket) -> Unit) {
        cancel()
        this.listener = SafeListener(listener)
        val runnable = SearchRunnable(calendar, factory, handler, this.listener)
        future = executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES)
    }

    fun cancel() {
        listener.dispose()
        future?.cancel(true)
    }

    private class SearchRunnable(
        private val calendar: Calendar,
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
            return when {
                time < FLY_OFF -> GifBucketRequest.WaitingRequest()
                time < FIRST_LIFT -> GifBucketRequest.PartyRequest()
                time < SKI_CLOSING && isSkyHours(time) -> GifBucketRequest.SkiRequest()
                time < FLY_HOME -> GifBucketRequest.PartyRequest()
                else -> GifBucketRequest.ByeRequest()
            }
        }

        private fun isSkyHours(time: Long): Boolean {
            calendar.timeInMillis = time
            val totalMin = calendar.get(Calendar.MINUTE) + calendar.get(Calendar.HOUR_OF_DAY) * 60
            return totalMin in (SKI_OPEN + 1)..(SKI_CLOSE - 1)
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
            private val emptyListener: (GifBucket) -> Unit = { /* empty */ }

            fun empty() = SafeListener(emptyListener)
        }
    }

    companion object {
        private const val FLY_OFF = 1548523800000L
        private const val FIRST_LIFT = 1548574200000L
        private const val SKI_CLOSING = 1549038600000L
        private const val FLY_HOME = 1549123200000L

        private const val SKI_OPEN = 450 // 7:30 in UTC
        private const val SKI_CLOSE = 990 // 16:30 in UTC
    }
}