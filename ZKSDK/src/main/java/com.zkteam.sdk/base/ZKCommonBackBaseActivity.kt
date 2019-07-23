package com.zkteam.sdk.base

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SizeUtils
import com.zkteam.sdk.R
import com.zkteam.sdk.swipepanel.ZKSwipePanel
import com.zkteam.sdk.swipepanel.ZKSwipePanelX

abstract class ZKCommonBackBaseActivity : AppCompatActivity() {

    abstract fun isSwipeBack(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<FrameLayout>(android.R.id.content).setBackgroundColor(resources.getColor(R.color.white_back))
        initSwipeBack()
    }

    private fun initSwipeBack() {
        if (isSwipeBack()) {
            val swipePanel  = ZKSwipePanel(this)
            swipePanel.setLeftDrawable(R.drawable.zk_base_back)
            swipePanel.setLeftEdgeSize(SizeUtils.dp2px(100F))
            swipePanel.wrapView(findViewById(android.R.id.content))
            swipePanel.setOnFullSwipeListener(object : ZKSwipePanelX.OnFullSwipeListener {
                override fun onFullSwipe(direction: Int) {
                    swipePanel.close(direction)
                    finish()
                }

            })
        }
    }

}