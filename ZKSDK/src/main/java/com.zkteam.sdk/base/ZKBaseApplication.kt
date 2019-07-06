package com.zkteam.sdk.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ProcessUtils

class ZKBaseApplication : Application() {

    companion object {
        // 双重校验锁式（Double Check)
        val instance: ZKBaseApplication by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZKBaseApplication()
        }
    }

    lateinit var context :Context

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ZKBaseApplication.instance.context = this
        MultiDex.install(this)
    }

    fun isDebug(): Boolean {
        return AppUtils.isAppDebug()
    }

    fun isMainProcess(): Boolean {
        return ProcessUtils.isMainProcess()
    }

}