package com.zkteam.sdk.demo

import android.app.Application
import com.zkteam.sdk.ZKManager

class ZKSDKApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ZKManager.instance.init(this)
    }
}