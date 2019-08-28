package com.github.ktomoyasu.mppsample.common

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object Preference {
    private lateinit var pref: SharedPreferences

    fun setUp(context: Context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    actual fun get(key: String): String {
        return pref.getString(key, "")
    }

    actual fun put(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }
}


internal actual val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO