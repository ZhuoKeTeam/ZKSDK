package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.zkteam.sdk.R

abstract class ZKCommonDrawerActivity : ZKBaseActivity() {

    lateinit var mBaseDrawerRootLayout: DrawerLayout
    lateinit var mBaseDrawerContainerView: FrameLayout

    private val mListener = object : NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
            val id = item.itemId
            return onDrawerItemClickListener(id)
        }
    }

    /**
     *  if (id == R.id.baseDrawerActionGitHub) {
     *      ToastUtils.showShort("github")
     *      return true
     *  } else if (id == R.id.baseDrawerActionBlog) {
     *      ToastUtils.showShort("blog")
     *      return true
     *  }
     *  return false
     */
    abstract fun onDrawerItemClickListener(itemId: Int): Boolean

    /**
     * 布局请保留 R.layout.activity_zk_common_drawer 中的 id，否则可能无效，或者重新自行获取
     */
    abstract fun setDrawerLayout(): Int

    override fun isSwipeBack(): Boolean {
        return false
    }

    @SuppressLint("ResourceType")
    override fun setRootLayout(@LayoutRes layoutId: Int) {
        val drawerLayoutId = setDrawerLayout()
        if (drawerLayoutId == 0) {
            super.setRootLayout(R.layout.activity_zk_common_drawer)
        } else {
            super.setRootLayout(drawerLayoutId)
        }
        mBaseDrawerRootLayout = findViewById(R.id.baseDrawerRootLayout)
        mBaseDrawerContainerView = findViewById(R.id.baseDrawerContainerView)
        if (layoutId > 0) {
            LayoutInflater.from(this).inflate(layoutId, mBaseDrawerContainerView)
        }
        val nav = findViewById<NavigationView>(R.id.baseDrawerNavView)
        nav.setNavigationItemSelectedListener(mListener)
    }



}