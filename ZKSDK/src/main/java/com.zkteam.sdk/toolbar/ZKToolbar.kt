package com.zkteam.sdk.toolbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.zkteam.sdk.R

class ZKToolbar : Toolbar {

    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    private fun init() {
        popupTheme = R.style.ZKToolbar_Style_Theme_Popup_Menu
        setBackgroundColor(context.resources.getColor(R.color.colorPrimary))
    }

    fun showCenterView() {
        title = ""
        contentInsetStartWithNavigation = 0
        setContentInsetsRelative(0, 0)
    }
}