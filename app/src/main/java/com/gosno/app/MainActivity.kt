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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dvoiss.geocities.Geocities
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

    override fun attachBaseContext(newBase: Context) {
        if (newBase.isOnion()) {
            super.attachBaseContext(Geocities.wrap(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
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
        setUpOnionNavigationView()
    }

    private fun setUpOnionNavigationView() {
        if (isOnion()) {
            navigationView.menu.findItem(R.id.traceSnow).title = getString(R.string.tinder)
        }
    }

    private fun isCurrentFragmentPisteMap(): Boolean =
            supportFragmentManager.findFragmentById(R.id.contentFrame) is PistesFragment

    private fun openTraceSnow() {
        val launchIntent = packageManager.getLaunchIntentForPackage(getAppPackageName())
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            openTraceSnowPlayStore()
        }
    }

    private fun openTraceSnowPlayStore() {
        val playStoreIntent = Intent(Intent.ACTION_VIEW)
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        playStoreIntent.data = Uri.parse("market://details?id=${getAppPackageName()}")
        startActivity(playStoreIntent)
    }

    private fun getAppPackageName() = if (isOnion()) {
        TINDER_PACKAGE_NAME
    } else {
        TRACE_SNOW_PACKAGE_NAME
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
        val requestBuilder = Glide.with(this)
                .asGif()
                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
        val imageView = navigationView.getHeaderView(0) as ImageView
        val service = LifecycleGifService { resource ->
            requestBuilder.load(resource)
                    .into(imageView)
        }
        service.attach(lifecycle)
        imageView.setOnClickListener { service.requestNewGif() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TRACE_SNOW_PACKAGE_NAME = "com.alpinereplay.android"
        private const val TINDER_PACKAGE_NAME = "com.tinder"

        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}
