package com.zkteam.sdk.sp

/**
 * 默认可以直接使用
 *
 * @see com.blankj.utilcode.util.SPUtils
 */
class ZKSDKSP : ZKSharedPreferences() {
    override fun sharedPreferencesFileName(): String {
        return SP_NAME
    }
}