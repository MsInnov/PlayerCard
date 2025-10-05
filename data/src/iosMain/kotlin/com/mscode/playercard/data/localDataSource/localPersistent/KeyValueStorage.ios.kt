package com.mscode.playercard.data.localDataSource.localPersistent

import platform.Foundation.NSUserDefaults

actual class KeyValueStorage actual constructor(context: Any?) {
    private val defaults = NSUserDefaults.standardUserDefaults()

    actual fun putString(key: String, value: String) {
        defaults.setObject(value, key)
    }

    actual fun getString(key: String, defaultValue: String): String {
        return defaults.stringForKey(key) ?: defaultValue
    }

    actual fun getAll(): Map<String, Any> {
        val dict = defaults.dictionaryRepresentation()
        return dict.keys
            .mapNotNull { it as? String }
            .mapNotNull { key ->
                val value = dict[key]
                if (value != null) key to value else null
            }
            .toMap()
    }
}