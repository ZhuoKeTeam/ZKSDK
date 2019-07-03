package com.zkteam.sdk.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter


abstract class ZKBaseAdapter<T>(layoutResId: Int, data: MutableList<T>?) :
    BaseQuickAdapter<T, ZKBaseViewHolder>(layoutResId, data)


//abstract class ZKBaseAdapter<T, K : ZKBaseViewHolder>  : BaseQuickAdapter<T, K> {
//
//    constructor(layoutResId: Int, data: MutableList<T>?) : super(layoutResId, data)
////    constructor(data: MutableList<T>?) : super(data)
////    constructor(layoutResId: Int) : super(layoutResId)
//
//}

