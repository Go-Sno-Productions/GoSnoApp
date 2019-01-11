package com.gosno.app.generalinfo

import android.content.Intent
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
        pranasTextView.setOnClickListener { openUrl("https://m.facebook.com/PranasGataveckas") }
        phoneTextView.setOnClickListener { callPhone() }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + getString(R.string.phone_number))
        startActivity(intent)
    }

    companion object {
        fun newInstance(): GeneralInfoFragment = GeneralInfoFragment()
    }
}