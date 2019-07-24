package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.drawerlayout.widget.DrawerLayout
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.navigation.NavigationView
import com.zkteam.sdk.R

abstract class ZKCommonDrawerActivity : ZKBaseActivity() {


    protected lateinit var mBaseDrawerRootLayout: DrawerLayout
    protected lateinit var mBaseDrawerContainerView: FrameLayout

    private val mListener = object : NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
            val id = item.itemId
            if (id == R.id.baseDrawerActionGitHub) {
                ToastUtils.showShort("github")
                return true
            } else if (id == R.id.baseDrawerActionBlog) {
                ToastUtils.showShort("blog")
                return true
            }
            return false
        }
    }

    override fun isSwipeBack(): Boolean {
        return false
    }

    @SuppressLint("ResourceType")
    override fun setRootLayout(@LayoutRes layoutId: Int) {
        super.setRootLayout(R.layout.activity_zk_common_drawer)
        mBaseDrawerRootLayout = findViewById(R.id.baseDrawerRootLayout)
        mBaseDrawerContainerView = findViewById(R.id.baseDrawerContainerView)
        if (layoutId > 0) {
            LayoutInflater.from(this).inflate(layoutId, mBaseDrawerContainerView)
        }
        val nav = findViewById<NavigationView>(R.id.baseDrawerNavView)
        nav.setNavigationItemSelectedListener(mListener)
    }

}