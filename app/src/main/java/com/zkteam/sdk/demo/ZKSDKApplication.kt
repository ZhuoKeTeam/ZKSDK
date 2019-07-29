package com.zkteam.sdk.demo

import com.zkteam.sdk.base.ZKBaseApplication
import com.zkteam.ui.components.ZKUIManager
import com.zkteam.ui.components.activity.ZKSplashActivity

class ZKSDKApplication : ZKBaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ZKSplashActivity.DELAY_TIME = 0
        ZKUIManager.instance.setMainActivity(MainActivity::class.java)
    }
}