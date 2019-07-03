package com.zkteam.sdk.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ProcessUtils

class ZKBaseAppplication : Application() {

    companion object {
        // 双重校验锁式（Double Check)
        val instance: ZKBaseAppplication by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZKBaseAppplication()
        }
    }

    lateinit var context :Context

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ZKBaseAppplication.instance.context = this
        MultiDex.install(this)
    }

    fun isDebug(): Boolean {
        return AppUtils.isAppDebug()
    }

    fun isMainProcess(): Boolean {
        return ProcessUtils.isMainProcess()
    }

}