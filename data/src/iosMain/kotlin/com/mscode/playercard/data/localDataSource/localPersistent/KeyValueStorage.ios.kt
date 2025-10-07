package com.mscode.playercard.data.localdatasource.localpersistent

import platform.Foundation.NSUserDefaults

actual open class KeyValueStorage actual constructor(context: Any?) {
    private val defaults = NSUserDefaults.standardUserDefaults()

    actual open fun putString(key: String, value: String) {
        defaults.setObject(value, key)
    }

    actual open fun getString(key: String, defaultValue: String): String {
        return defaults.stringForKey(key) ?: defaultValue
    }

    actual open fun getAll(): Map<String, Any> {
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