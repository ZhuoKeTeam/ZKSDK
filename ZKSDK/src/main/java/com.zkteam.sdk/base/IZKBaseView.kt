package com.zkteam.sdk.base

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable

interface IZKBaseView {

    fun getLayoutId(): Int
    fun initViews(@Nullable contentView: View)
    fun initListener()
    fun initLifecycleObserve()
    fun initData(@Nullable bundle: Bundle?)
    fun onDebouncingClick(view: View)
}