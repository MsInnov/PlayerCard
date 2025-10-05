package com.mscode.playercard.data.localDataSource.localPersistent

expect class KeyValueStorage(context: Any?) {
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): String
    fun getAll(): Map<String, Any>
}