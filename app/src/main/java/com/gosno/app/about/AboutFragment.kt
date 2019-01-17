package com.gosno.app.about

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gosno.app.R
import kotlinx.android.synthetic.main.fragment_about.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class AboutFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpImageViews()
        view.post { startKonfetti() }
    }

    private fun setUpImageViews() {
        loadCircleImageView(R.drawable.vaidas, vaidasImageView)
        loadCircleImageView(R.drawable.rokas, rokasImageView)
    }

    private fun loadCircleImageView(drawableResId: Int, imageView: ImageView) {
        Glide.with(context!!)
                .load(drawableResId)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    private fun startKonfetti() {
        konfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12))
                .setPosition(-50f, konfetti.width + 50f, -50f, -50f)
                .stream(300, 5000L)
    }

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }
}