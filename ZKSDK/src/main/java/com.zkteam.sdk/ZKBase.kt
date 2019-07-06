package com.zkteam.sdk

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.zkteam.sdk.exception.ZKBaseException

class ZKBase private constructor() {

    init {
        throw ZKBaseException("u can't instantiate me...")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null
        var isDebug = AppUtils.isAppDebug()

        /**
         * 初始化工具类 (推荐)
         */
        fun init(@NonNull app: Application) {
            init(app, false)
        }

        fun context(): Context {
            if (mContext == null) {
                throw NullPointerException("请优先在 Application 中初始化 ZKManager.instance.init(this)")
            }
            return mContext!!
        }


        /**
         * 初始化工具类（可以直接设置 debug 的状态）
         */
        fun init(@NonNull app: Application, debug: Boolean) {
            mContext = app
            isDebug = debug

            Utils.init(app)
        }
    }
}