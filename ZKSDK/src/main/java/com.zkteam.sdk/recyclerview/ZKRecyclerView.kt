package com.zkteam.sdk.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ZKRecyclerView : RecyclerView {

    companion object {
        const val ZKRV_LINEAR_LAYOUT_H = 0
        const val ZKRV_LINEAR_LAYOUT_V = 1
        const val ZKRV_STAGGERED_GRID_H = 3
        const val ZKRV_STAGGERED_GRID_V = 4
        const val ZKRV_GRID_VIEW_H = 5
        const val ZKRV_GRID_VIEW_V = 6
    }

    // 瀑布流两行/两列
    private var DEFAULT_STAGGEREDGRID_COUNT = 2
    // 网格布局
    private var DEFAULT_GRIDVIEW_COUNT = 2

    constructor(context: Context) : super(context) { init() }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) { init() }


    private fun init() {
        setLayoutManager(ZKRV_LINEAR_LAYOUT_V)
    }

    public fun setStaggeredGridSpanCount(spanCount: Int) {
        DEFAULT_STAGGEREDGRID_COUNT = spanCount
    }

    public fun setGridViewSpanCount(spanCount: Int) {
        DEFAULT_GRIDVIEW_COUNT = spanCount
    }

    public fun setLayoutManager(type: Int) = when(type) {
        ZKRV_LINEAR_LAYOUT_H -> {
            val llManager = LinearLayoutManager(context)
            llManager.orientation = HORIZONTAL
            layoutManager = llManager
        }
        ZKRV_LINEAR_LAYOUT_V -> {
            val llManager = LinearLayoutManager(context)
            llManager.orientation = VERTICAL
            layoutManager = llManager
        }
        ZKRV_STAGGERED_GRID_H -> {
            setHasFixedSize(true)
            val sglManager = StaggeredGridLayoutManager(DEFAULT_STAGGEREDGRID_COUNT, HORIZONTAL)
            layoutManager = sglManager
        }
        ZKRV_STAGGERED_GRID_V -> {
            setHasFixedSize(true)
            val sglManager = StaggeredGridLayoutManager(DEFAULT_STAGGEREDGRID_COUNT, VERTICAL)
            layoutManager = sglManager
        }
        ZKRV_GRID_VIEW_H -> {
            val glManager = GridLayoutManager(context, DEFAULT_GRIDVIEW_COUNT)
            glManager.orientation = HORIZONTAL
            layoutManager = glManager
        }
        ZKRV_GRID_VIEW_V -> {
            val glManager = GridLayoutManager(context, DEFAULT_GRIDVIEW_COUNT)
            glManager.orientation = VERTICAL
            layoutManager = glManager
        }
        else -> {
            val llManager = LinearLayoutManager(context)
            llManager.orientation = VERTICAL
            layoutManager = llManager
        }

    }




}