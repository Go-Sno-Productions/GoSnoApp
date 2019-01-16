package com.gosno.app.onionrepository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class OnionRepository {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (isFirstTime()) {
            setIsOnion(true)
            setFirstTimeFalse()
        }
    }

    private fun isFirstTime() = getBoolean(KEY_IS_FIRST_TIME, true)

    private fun setFirstTimeFalse() = saveBoolean(KEY_IS_FIRST_TIME, false)

    fun isOnion() = getBoolean(KEY_IS_ONION, false)

    fun setIsOnion(enable: Boolean) = saveBoolean(KEY_IS_ONION, enable)

    private fun saveBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

    private fun getBoolean(key: String, defaultValue: Boolean) = sharedPreferences.getBoolean(key, defaultValue)

    companion object {
        private const val KEY_IS_ONION = "key.isOnion"
        private const val KEY_IS_FIRST_TIME = "key.isFirstTime"
    }
}