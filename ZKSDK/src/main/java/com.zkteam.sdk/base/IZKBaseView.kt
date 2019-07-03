package com.zkteam.sdk.base

import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View

interface IZKBaseView {

    fun getLayoutId(): Int
    fun initViews(@Nullable contentView: View)
    fun initListener()
    fun initLifecycleObserve()
    fun initData(@Nullable bundle: Bundle)
    fun onDebouncingClick(view: View)
}