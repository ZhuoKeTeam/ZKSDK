package com.zkteam.sdk.toolbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import com.zkteam.sdk.R

class ZKToolbar : Toolbar {

    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    private fun init() {
        val rootView = LayoutInflater.from(context).inflate(R.layout.zk_toolbar_layout, null)

        val toolbar = rootView.findViewById<Toolbar>(R.id.zk_tool_bar)

        toolbar.contentInsetStartWithNavigation = 0
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setSubtitleTextColor(Color.WHITE)

        addView(rootView)
        setBackgroundColor(resources.getColor(R.color.colorPrimary))
    }
}