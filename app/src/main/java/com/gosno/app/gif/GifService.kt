package com.gosno.app.gif

import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class GifService : (GifBucket) -> Unit {
    private val handler = Handler(Looper.getMainLooper())
    private val factory = GifBucketFactory()
    private val calendar = Calendar.getInstance().apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val executorService = Executors.newSingleThreadScheduledExecutor { run -> Thread(run, "GifScheduler") }
    private var future: Future<*>? = null
    private var listener = emptyListener

    fun start(listener: (GifBucket) -> Unit) {
        cancel()
        this.listener = listener
        val runnable = SearchRunnable(calendar, factory, this)
        future = executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES)
    }

    fun cancel() {
        listener = emptyListener
        future?.cancel(true)
    }

    override fun invoke(bucket: GifBucket) {
        handler.post { listener(bucket) }
    }

    private class SearchRunnable(
        private val calendar: Calendar,
        private val bucketFactory: GifBucketFactory,
        private val listener: (GifBucket) -> Unit
    ) : Runnable {
        private var lastRequest: GifBucketRequest? = null

        override fun run() {
            val request = getRequest(System.currentTimeMillis())
            if (request != lastRequest) {
                listener(bucketFactory.provide(request))
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

    companion object {
        private const val FLY_OFF = 1548523800000L
        private const val FIRST_LIFT = 1548574200000L
        private const val SKI_CLOSING = 1549038600000L
        private const val FLY_HOME = 1549123200000L

        private const val SKI_OPEN = 450 // 7:30 in UTC
        private const val SKI_CLOSE = 990 // 16:30 in UTC

        private val emptyListener: (GifBucket) -> Unit = { /* empty */ }
    }
}