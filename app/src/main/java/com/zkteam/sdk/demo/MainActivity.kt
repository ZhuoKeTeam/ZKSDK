package com.zkteam.sdk.demo

import android.os.Bundle
import android.view.View
import com.zkteam.sdk.ZKBase
import com.zkteam.sdk.base.ZKBaseActivity
import com.zkteam.sdk.base.ZKBaseAppplication

class MainActivity : ZKBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(contentView: View) {
        //function
    }

    override fun initListener() {
        //function
    }

    override fun initLifecycleObserve() {
        //function
    }

    override fun initData(bundle: Bundle) {
        ZKBase.isDebug
        ZKBase.context()
        ZKBaseAppplication.getInstance().isDebug()
        ZKBaseAppplication.getInstance().isMainProcess()
    }

    override fun onDebouncingClick(view: View) {
        //function
    }


}
