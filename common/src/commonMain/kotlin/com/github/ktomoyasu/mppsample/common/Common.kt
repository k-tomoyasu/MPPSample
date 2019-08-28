package com.github.ktomoyasu.mppsample.common


expect object Preference {
    fun put(key: String, value: String)
    fun get(key: String): String
}
const val KEY_LAST_SEARCH = "LAST_SEARCHED"
