package com.gosno.app.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gosno.app.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.newIntent(this))
        finish()
    }
}