package com.zkteam.sdk.demo

import android.os.Bundle
import android.view.View
import com.zkteam.sdk.ZKBase
import com.zkteam.sdk.base.ZKBaseActivity
import com.zkteam.sdk.base.ZKBaseAppplication
import com.zkteam.sdk.recyclerview.ZKRecyclerView
import com.zkteam.sdk.recyclerview.adapter.ZKTextAdapter
import com.zkteam.sdk.recyclerview.adapter.ZKTextBean
import com.zkteam.sdk.testdata.TestBeautyData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ZKBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(contentView: View) {

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
        //function
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
