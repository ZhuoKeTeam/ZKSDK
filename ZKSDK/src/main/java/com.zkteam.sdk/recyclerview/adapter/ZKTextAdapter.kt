package com.zkteam.sdk.recyclerview.adapter

import com.blankj.utilcode.util.SpanUtils
import com.zkteam.sdk.recyclerview.ZKBaseAdapter
import com.zkteam.sdk.recyclerview.ZKBaseViewHolder
import com.zkteam.sdk.testdata.TestBeautyData

class ZKTextAdapter(data: MutableList<ZKTextBean>?) :
    ZKBaseAdapter<ZKTextBean>(android.R.layout.simple_list_item_1, data) {
    override fun convert(helper: ZKBaseViewHolder, item: ZKTextBean?) {
        if (item != null) {
            helper.setText(android.R.id.text1, item.text)

            SpanUtils.with(helper.getView(android.R.id.text1)).append("${item.text} ")
                .appendLine("${item.index}").setFontSize(10, true).setSuperscript()
                .create()

        }
    }
}

data class ZKTextBean(var text: String, var index: Int)

object ZKTextData {

    fun getData(): MutableList<ZKTextBean> {
        val list = mutableListOf<ZKTextBean>()

        for ((index, name) in TestBeautyData.getNameForHLWHotWomen().withIndex()) {
            list.add(ZKTextBean(name, index))
        }

        list.addAll(list)
        list.addAll(list)
        return list
    }
}