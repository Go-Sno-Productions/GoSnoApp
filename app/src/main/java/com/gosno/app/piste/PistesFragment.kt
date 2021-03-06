package com.gosno.app.piste

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gosno.app.R
import com.gosno.app.isOnion
import kotlinx.android.synthetic.main.fragment_pistes.*

class PistesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pistes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView.showImage(getSkiMapUri())
    }

    private fun getSkiMapUri() = getUri(getSkiMapResId())

    private fun getSkiMapResId() = if (context?.isOnion() == true) {
        R.drawable.img_onion_skimap
    } else {
        R.drawable.img_skimap
    }

    private fun getUri(resourceId: Int): Uri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resourceId))
        .appendPath(resources.getResourceTypeName(resourceId))
        .appendPath(resources.getResourceEntryName(resourceId))
        .build()

    companion object {
        fun newInstance(): PistesFragment = PistesFragment()
    }
}