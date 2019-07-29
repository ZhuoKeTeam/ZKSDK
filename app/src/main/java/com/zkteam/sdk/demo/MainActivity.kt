package com.zkteam.sdk.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.zkteam.sdk.ZKBase
import com.zkteam.sdk.base.ZKBaseActivity
import com.zkteam.sdk.base.ZKBaseApplication
import com.zkteam.sdk.recyclerview.ZKRecyclerView
import com.zkteam.sdk.recyclerview.adapter.ZKTextAdapter
import com.zkteam.sdk.recyclerview.adapter.ZKTextData
import com.zkteam.sdk.sp.ZKSDKSP
import com.zkteam.sdk.utils.L
import com.zkteam.sdk.utils.ShortCutsCreator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ZKBaseActivity() {

    private var count: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getToolbarMenu(): Int {
        return R.menu.menu_main
    }

    override fun initViews(contentView: View) {
        ZKBase.init(this.application, true)

        SPUtils.getInstance("spName").put("key", "value")

        ZKSDKSP().put("key", "value")
        L.d("value= ${ZKSDKSP()["key", "默认值"]}")

        val zkRecyclerView = contentView.findViewById<ZKRecyclerView>(R.id.zk_rv)

        zkRecyclerView.setLayoutManager(ZKRecyclerView.ZKRV_GRID_VIEW_V)
        zkRecyclerView.adapter = ZKTextAdapter(ZKTextData.getData())
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
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://zkteam.cc/"))
                )//R.mipmap.ic_add
        }
    }

    override fun initLifecycleObserve() {
        //function
    }

    override fun initData(bundle: Bundle?) {
        ZKBase.isDebug
        ZKBase.context()
        ZKBaseApplication.instance.isDebug()
        ZKBaseApplication.instance.isMainProcess()
    }

    override fun onDebouncingClick(view: View) {
        //function
    }


}
