package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.ClickUtils

abstract class ZKBaseActivity : ZKCommonBackBaseActivity(), IZKBaseView {

    private var useSwipePanel = true

    lateinit var mContentView: View
    lateinit var mContext: Activity

    private val mClickListener = View.OnClickListener { v -> onDebouncingClick(v) }

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
        super.onCreate(savedInstanceState)
        setRootLayout(getLayoutId())
        initViews(mContentView)
        initListener()
        initLifecycleObserve()
        initData(intent.extras)
    }

    @SuppressLint("ResourceType")
    open fun setRootLayout(@LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    fun applyDebouncingClickListener(vararg views: View) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener)
        ClickUtils.applyScale(*views)
    }

    override fun isSwipeBack(): Boolean {
        return useSwipePanel
    }

    fun setSwipeBack(useSwipePanel: Boolean) {
        this.useSwipePanel = useSwipePanel
    }

}