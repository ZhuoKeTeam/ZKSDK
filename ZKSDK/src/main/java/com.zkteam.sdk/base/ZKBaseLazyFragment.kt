package com.zkteam.sdk.base

import android.os.Bundle

public abstract class ZKBaseLazyFragment : ZKBaseFragment() {

    private val TAG = "ZKBaseLazyFragment"

    private var isDataLoaded: Boolean = false

    abstract fun doLazyBusiness()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mContentView != null && !isDataLoaded) {
            doLazyBusiness()
            isDataLoaded = true
        }
    }

    override fun initData(bundle: Bundle?) {
        if (userVisibleHint && !isDataLoaded) {
            doLazyBusiness()
            isDataLoaded = true
        }
    }

}