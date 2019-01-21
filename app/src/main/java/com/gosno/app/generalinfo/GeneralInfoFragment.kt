package com.gosno.app.generalinfo

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gosno.app.R
import kotlinx.android.synthetic.main.fragment_general_info.*


class GeneralInfoFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_general_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotelAvantiTextView.setOnClickListener { openUrl("https://goo.gl/maps/RquWscYrhfp") }
        hotelCimsTextView.setOnClickListener { openUrl("https://goo.gl/maps/1PzeeUGor2S2") }
        pranasTextView.setOnClickListener { openFacebook("https://facebook.com/PranasGataveckas") }
        andorraGroupTextView.setOnClickListener { openFacebook("https://www.facebook.com/groups/2477585492256152/") }
        phoneTextView.setOnClickListener { callPhone() }
        webTextView.setOnClickListener {
            openUrl("https://www.wegoproject.lt/slidinejmas-andoroje-grandvalira-2019.html")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun openFacebook(url: String) = try {
        val applicationInfo = context!!.packageManager.getApplicationInfo(FACEBOOK_PACKAGE, 0)
        val uri = if (applicationInfo.enabled) {
            Uri.parse(FACEBOOK_ACTION + url)
        } else {
            Uri.parse(url)
        }
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (ignored: PackageManager.NameNotFoundException) {
        // Empty
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + getString(R.string.phone_number))
        startActivity(intent)
    }

    companion object {
        private const val FACEBOOK_PACKAGE = "com.facebook.katana"
        private const val FACEBOOK_ACTION = "fb://facewebmodal/f?href="

        fun newInstance(): GeneralInfoFragment = GeneralInfoFragment()
    }
}