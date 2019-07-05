package com.zkteam.sdk.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.zkteam.sdk.ZKBase
import com.zkteam.sdk.base.ZKBaseActivity
import com.zkteam.sdk.base.ZKBaseAppplication
import com.zkteam.sdk.recyclerview.ZKRecyclerView
import com.zkteam.sdk.recyclerview.adapter.ZKTextAdapter
import com.zkteam.sdk.recyclerview.adapter.ZKTextBean
import com.zkteam.sdk.testdata.TestBeautyData
import com.zkteam.sdk.utils.ShortCutsCreator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ZKBaseActivity() {

    private var count: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(contentView: View) {

        ZKBase.init(this.application)

        val list = mutableListOf<ZKTextBean>()

        for ((index, name) in TestBeautyData.getNameForHLWHotWomen().withIndex()) {
            list.add(ZKTextBean(name, index))
        }

        list.addAll(list)
        list.addAll(list)

        zk_rv.setLayoutManager(ZKRecyclerView.ZKRV_GRID_VIEW_V)
        zk_rv.adapter = ZKTextAdapter(list)
    }

    override fun initListener() {
        count++
        bt.setOnClickListener {
            ShortCutsCreator()
                .createShortCut(
                    "id$count",
                    "id$count",
                    "long-id$count",
                    0, "disabled",
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://zkteam.cc/")))//R.mipmap.ic_add
        }
    }

    override fun initLifecycleObserve() {
        //function
    }

    override fun initData(bundle: Bundle?) {
        ZKBase.isDebug
        ZKBase.context()
        ZKBaseAppplication.instance.isDebug()
        ZKBaseAppplication.instance.isMainProcess()
    }

    override fun onDebouncingClick(view: View) {
        //function
    }


}
