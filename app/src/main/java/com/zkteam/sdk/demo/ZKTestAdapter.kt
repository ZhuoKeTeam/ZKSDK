package com.zkteam.sdk.demo

import com.zkteam.sdk.recyclerview.ZKBaseAdapter
import com.zkteam.sdk.recyclerview.ZKBaseViewHolder

class ZKTestAdapter(data: MutableList<ZKTestBean>) : ZKBaseAdapter<ZKTestBean>
    (android.R.layout.simple_list_item_1, data) {

    override fun convert(helper: ZKBaseViewHolder, item: ZKTestBean) {
        if (item != null) {
            helper.setText(android.R.id.text1, item.name)
        }
    }
}

data class ZKTestBean(var name: String)