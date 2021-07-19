package com.zkteam.sdk.base

import android.os.Bundle
import android.view.View

/**
 * 【summary】     : 设置 View 的基础属性
 * 【Create By】   : wangqing
 * 【Create Time】 : 2021/7/19 6:49 下午
 */
internal interface IZKBaseView {
    /**
     * 设置资源 layout 的 id
     * @return 资源 id
     */
    fun getLayoutId():Int

    /**
     * 初始化 View
     * @param contentView View
     */
    fun initViews(contentView: View)

    /**
     * 设置 监听器
     */
    fun initListener()

    /**
     * 设置 Lifecycle 的 Observe
     */
    fun initLifecycleObserve()

    /**
     * 初始化数据
     * @param bundle Bundle
     */
    fun initData(bundle: Bundle?)

    /**
     * 防止 View 多次点击
     * @param view View
     */
    fun onDebouncingClick(view: View)
}