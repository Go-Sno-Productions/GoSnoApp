package com.gosno.app.gif

sealed class GifBucketRequest {
    class WaitingRequest : GifBucketRequest()
    class PartyRequest : GifBucketRequest()
    class SkiRequest : GifBucketRequest()
    class ByeRequest : GifBucketRequest()
}