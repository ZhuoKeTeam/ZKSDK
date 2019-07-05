package com.zkteam.sdk.utils

import android.util.Log
import com.zkteam.sdk.ZKBase
import java.util.*

object L {
    private const val TAG = "ZK_SDK_TAG"

    private var isDebug = ZKBase.isDebug

    fun i(vararg msg: Any) {
        if (isDebug) {
            val msgInfo = Arrays.toString(msg)
            Log.i(TAG, msgInfo)
        }

    }

    fun d(vararg msg: Any) {
        if (isDebug) {
            val msgInfo = Arrays.toString(msg)
            Log.d(TAG, msgInfo)
        }

    }

    fun w(vararg msg: Any) {
        if (isDebug) {
            val msgInfo = Arrays.toString(msg)
            Log.w(TAG, msgInfo)
        }

    }

    fun e(vararg msg: Any) {
        if (isDebug) {
            val msgInfo = Arrays.toString(msg)
            Log.e(TAG, msgInfo)
        }

    }

    fun m(vararg msg: Any) {
        if (isDebug) {
            val msgInfo = Arrays.toString(msg)
            Log.d(TAG, msgInfo)
        }

    }
}