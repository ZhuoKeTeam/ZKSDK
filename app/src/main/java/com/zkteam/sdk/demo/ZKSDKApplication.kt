package com.zkteam.sdk.demo

import com.zkteam.sdk.base.ZKBaseApplication
import com.zkteam.ui.components.ZKUIManager

class ZKSDKApplication : ZKBaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ZKUIManager.instance.setMainActivity(MainActivity::class.java)
    }
}