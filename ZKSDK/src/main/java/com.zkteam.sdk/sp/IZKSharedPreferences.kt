package com.zkteam.sdk.sp

import com.zkteam.sdk.exception.ZKSPException

interface IZKSharedPreferences {
    @Throws(ZKSPException::class)
    fun put(key: String, defaultObject: Any)

    @Throws(ZKSPException::class)
    operator fun get(key: String, defaultObject: Any): Any?

    @Throws(ZKSPException::class)
    fun remove(key: String)

    @Throws(ZKSPException::class)
    fun clean()

    @Throws(ZKSPException::class)
    fun contain(key: String): Boolean

    @Throws(ZKSPException::class)
    fun getAll(): Map<String, *>?
}