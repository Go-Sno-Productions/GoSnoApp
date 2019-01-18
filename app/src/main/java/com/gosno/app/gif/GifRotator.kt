package com.gosno.app.gif

class GifRotator(gifBucket: GifBucket) {
    private val resources = gifBucket.gifResources.shuffled()
    private var position: Int = 0

    fun nextId(): Int {
        val resource = resources[position++]
        if (position > resources.lastIndex) position = 0
        return resource
    }
}