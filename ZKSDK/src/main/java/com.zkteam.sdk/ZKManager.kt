package com.zkteam.sdk

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.StrictMode

class ZKManager {

    private lateinit var mContext: Context

    companion object {
        // 双重校验锁式（Double Check)
        val instance: ZKManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZKManager()
        }
    }

    fun init(application: Application) {
        mContext = application
        ZKBase.init(application)
    }


    /**
     * android 7.0系统解决拍照的问题
     */
    fun initTakePhotoForApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // android 7.0系统解决拍照的问题
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }
    }

}