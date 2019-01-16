package com.gosno.app.gif

data class GifBucket(val gifResources: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifBucket

        return gifResources.contentEquals(other.gifResources)
    }

    override fun hashCode(): Int {
        return gifResources.contentHashCode()
    }
}