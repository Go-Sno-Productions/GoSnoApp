package com.gosno.app.gif

data class GifBucket(val id: Int, val gifResources: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifBucket

        return id == other.id && gifResources.contentEquals(other.gifResources)
    }

    override fun hashCode(): Int {
        return 31 * id + gifResources.contentHashCode()
    }

    companion object {
        fun waiting() = GifBucket(0, intArrayOf())
        fun party() = GifBucket(1, intArrayOf())
        fun skiing() = GifBucket(2, intArrayOf())
        fun bye() = GifBucket(3, intArrayOf())
    }
}