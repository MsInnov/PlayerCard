package com.mscode.playercard.data.localdatasource.localpersistent

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager

actual class KeyValueStorage actual constructor(context: Any?) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context as Context)

    actual fun putString(key: String, value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            prefs.edit().putString(key, value).apply()
        }
    }

    actual fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    actual fun getAll(): Map<String, Any> {
        return prefs.all.mapNotNull { (key, value) ->
            value?.let { key to it }
        }.toMap()
    }
}