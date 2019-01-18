package com.gosno.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gosno.app.about.AboutFragment
import com.gosno.app.generalinfo.GeneralInfoFragment
import com.gosno.app.gif.LifecycleGifService
import com.gosno.app.piste.PistesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNavigationView()
        setUpToolbar()
        setUpFragment()
        setUpHeader()
    }

    private fun setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.home -> {
                    if (!isCurrentFragmentPisteMap()) {
                        openPisteMapScreen()
                    }
                    true
                }
                R.id.generalInfo -> {
                    openGeneralInfoScreen()
                    true
                }
                R.id.traceSnow -> {
                    openTraceSnow()
                    false
                }
                R.id.about -> {
                    openAboutScreen()
                    false
                }
                else -> true
            }
        }
        navigationView.setCheckedItem(R.id.home)
    }

    private fun isCurrentFragmentPisteMap(): Boolean =
        supportFragmentManager.findFragmentById(R.id.contentFrame) is PistesFragment

    private fun openTraceSnow() {
        val launchIntent = packageManager.getLaunchIntentForPackage(TRACE_SNOW_PACKAGE_NAME)
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            openTraceSnowPlayStore()
        }
    }

    private fun openTraceSnowPlayStore() {
        val playStoreIntent = Intent(Intent.ACTION_VIEW)
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        playStoreIntent.data = Uri.parse("market://details?id=$TRACE_SNOW_PACKAGE_NAME")
        startActivity(playStoreIntent)
    }

    private fun setUpFragment() = openPisteMapScreen()

    private fun openPisteMapScreen() = openFragment(PistesFragment.newInstance())

    private fun openGeneralInfoScreen() = openFragment(GeneralInfoFragment.newInstance())

    private fun openAboutScreen() = openFragment(AboutFragment.newInstance())

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, fragment)
        transaction.commit()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    private fun setUpHeader() {
        val requestOptions = RequestOptions().centerCrop()
            .placeholder(R.mipmap.ic_launcher)
        val requestBuilder = Glide.with(this)
            .asGif()
            .apply(requestOptions)
        val imageView = navigationView.getHeaderView(0) as ImageView
        val service = LifecycleGifService { resource ->
            requestBuilder.load(resource)
                .into(imageView)
        }
        service.attach(lifecycle)
        imageView.setOnClickListener { service.requestNewGif() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)

        private const val TRACE_SNOW_PACKAGE_NAME = "com.alpinereplay.android"
    }
}
