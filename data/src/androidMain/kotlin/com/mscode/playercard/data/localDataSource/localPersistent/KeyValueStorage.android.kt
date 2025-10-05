package com.mscode.playercard.data.localDataSource.localPersistent

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager

actual open class KeyValueStorage actual constructor(context: Any?) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context as Context)

    actual open fun putString(key: String, value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            prefs.edit().putString(key, value).apply()
        }
    }

    actual open fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    actual open fun getAll(): Map<String, Any> {
        return prefs.all.mapNotNull { (key, value) ->
            value?.let { key to it }
        }.toMap()
    }
}