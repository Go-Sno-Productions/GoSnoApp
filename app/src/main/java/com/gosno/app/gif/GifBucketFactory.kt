package com.gosno.app.gif

class GifBucketFactory {
    fun provide(request: GifBucketRequest): GifBucket {
        return when (request) {
            is GifBucketRequest.PartyRequest -> GifBucket(intArrayOf())
            is GifBucketRequest.SkiRequest -> GifBucket(intArrayOf())
            is GifBucketRequest.ByeRequest -> GifBucket(intArrayOf())
            is GifBucketRequest.WaitingRequest -> GifBucket(intArrayOf())
        }
    }
}