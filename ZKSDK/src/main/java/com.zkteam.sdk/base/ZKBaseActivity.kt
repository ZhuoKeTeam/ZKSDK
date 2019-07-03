package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils

public abstract class ZKBaseActivity : AppCompatActivity(), IZKBaseView {


    protected lateinit var mContentView: View
    protected lateinit var mContext: Activity

    private val mClickListener = View.OnClickListener { v -> onDebouncingClick(v) }

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
        super.onCreate(savedInstanceState)
        setRootLayout(getLayoutId())
        initViews(mContentView)
        initListener()
        initLifecycleObserve()
        initData(intent.extras!!)
    }

    @SuppressLint("ResourceType")
    private fun setRootLayout(@LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    fun applyDebouncingClickListener(vararg views: View) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener)
        ClickUtils.applyScale(*views)
    }


}