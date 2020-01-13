package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.app.Activity
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
import com.zkteam.sdk.toolbar.ZKToolbar

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

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar)

        initListener()
        initLifecycleObserve()
        initData(intent.extras)
    }

    override fun initLifecycleObserve() {
        //function
        setLifecycleObserve()
    }

    open fun setLifecycleObserve() {
    }

    override fun onDebouncingClick(view: View) {
        setDebouncingClick(view)
    }

    open fun setDebouncingClick(view: View) {
    }

    override fun initListener() {
        setListener()
    }

    open fun setListener() {
    }

    open fun initToolbar(toolbar: Toolbar?) {
        var newToolbar = getToolbar()

        if (newToolbar == null) {
            newToolbar = toolbar
        }

        if (newToolbar != null) {
            setToolbarContent(newToolbar)
            setSupportActionBar(newToolbar)
            setToolbarListener(newToolbar)
        }
    }

    open fun showToolbarCenterView(toolbar: ZKToolbar) {
        toolbar.showCenterView()
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    open fun showToolbarCenterViewWithLeftBack(toolbar: ZKToolbar) {
        supportActionBar?.title = ""
        toolbar.showCenterView()
    }

    open fun setToolbarListener(toolbar: Toolbar) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        if (getToolbarMenu() > 0) {
            // 可以复写该方法
            toolbar.setOnMenuItemClickListener {
                ToastUtils.showShort(it.title)
                true
            }
        }
    }

    open fun setToolbarContent(toolbar: Toolbar) {
        //function
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