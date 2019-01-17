package com.gosno.app.gif

data class GifBucket(val id: Int, val gifResources: List<Int>) {
    companion object {
        // TODO provide resources for gif buckets

        fun waiting() = GifBucket(0, listOf())
        fun party() = GifBucket(1, listOf())
        fun skiing() = GifBucket(2, listOf())
        fun bye() = GifBucket(3, listOf())
    }
}