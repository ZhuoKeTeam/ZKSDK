package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkteam.sdk.R

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
        initToolbar()


        initListener()
        initLifecycleObserve()
        initData(intent.extras)
    }

    open fun initToolbar(): Toolbar? {
        val toolbar = getToolbar()
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            toolbar.setNavigationOnClickListener {
                finish()
            }

            toolbar.popupTheme = R.style.ZKToolbar_Style_Theme_Popup_Menu

            toolbar.contentInsetStartWithNavigation = 0
            toolbar.setTitleTextColor(Color.WHITE)
            toolbar.setSubtitleTextColor(Color.WHITE)

            if (getToolbarMenu() > 0) {
                toolbar.setOnMenuItemClickListener {
                    var text = "点击了 menu."
                    ToastUtils.showShort(text)

                    true
                }
            }
        }
        return toolbar
    }

    @SuppressLint("ResourceType")
    open fun setRootLayout(@LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuId = getToolbarMenu()
        if (menuId != 0) {
            menuInflater.inflate(getToolbarMenu(), menu)
            return true
        }

        return super.onCreateOptionsMenu(menu)
    }

    open fun getToolbarMenu(): Int {
        return 0
    }

    open fun getToolbar(): Toolbar? {
        return null
    }

    @SuppressLint("RestrictedApi")
    override fun onPrepareOptionsPanel(view: View?, menu: Menu): Boolean {
        if (menu.javaClass == MenuBuilder::class.java) {
            val m = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
            m.isAccessible = true
            m.invoke(menu, true)
        }
        return super.onPrepareOptionsPanel(view, menu)
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