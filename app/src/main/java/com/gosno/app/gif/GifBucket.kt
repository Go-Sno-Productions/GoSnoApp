package com.gosno.app.gif

import com.gosno.app.R

data class GifBucket(val id: Int, val gifResources: List<Int>) {
    companion object {
        fun waiting() = GifBucket(0, listOf(R.drawable.gif_wait))

        fun party() = GifBucket(
            1, listOf(
                R.drawable.gif_party_0,
                R.drawable.gif_party_1,
                R.drawable.gif_party_2,
                R.drawable.gif_party_3,
                R.drawable.gif_party_4,
                R.drawable.gif_party_5,
                R.drawable.gif_party_6
            )
        )

        fun skiing() = GifBucket(
            2, listOf(
                R.drawable.gif_ski_0,
                R.drawable.gif_ski_1,
                R.drawable.gif_ski_2,
                R.drawable.gif_ski_3,
                R.drawable.gif_ski_4,
                R.drawable.gif_ski_5,
                R.drawable.gif_ski_6,
                R.drawable.gif_ski_7,
                R.drawable.gif_ski_8,
                R.drawable.gif_ski_9
            )
        )

        fun bye() = GifBucket(3, listOf(R.drawable.gif_bye_0, R.drawable.gif_bye_1))
    }
}