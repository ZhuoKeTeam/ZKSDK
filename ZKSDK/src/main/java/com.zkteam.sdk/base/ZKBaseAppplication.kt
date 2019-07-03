package com.zkteam.sdk.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ProcessUtils

class ZKBaseAppplication : Application() {

    companion object {
        private lateinit var sInstance: ZKBaseAppplication

        fun getInstance(): ZKBaseAppplication {
            return sInstance
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        sInstance = this
        MultiDex.install(this)
    }

    fun isDebug(): Boolean {
        return AppUtils.isAppDebug()
    }

    fun isMainProcess(): Boolean {
        return ProcessUtils.isMainProcess()
    }

}