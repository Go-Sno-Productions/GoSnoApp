package com.gosno.app.generalinfo

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
        hotelAvantiTextView.setOnClickListener {  }
        hotelCimsTextView.setOnClickListener {  }
    }

    companion object {
        fun newInstance(): GeneralInfoFragment = GeneralInfoFragment()
    }
}