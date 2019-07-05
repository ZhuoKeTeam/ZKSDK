package com.zkteam.sdk.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.widget.Toast
import com.zkteam.sdk.ZKBase
import java.util.*

/**
 *
 * 在桌面的图标点击后，展示 icon, 点击跳转到我们 app 中的指定界面。
 *
 * Read from: http://www.trinea.cn/android/android-7-1-shortcuts-desc/
 */
class ShortCutsCreator {
    private lateinit var mContext: Context
    private var shortcutManager: ShortcutManager? = null

    /**
     * 添加一个 什么样的场景会用?
     *
     * @param id
     * @param shortlabel
     * @param longlabel
     * @param resId
     * @param disableMessage
     * @param pendingIntent
     */
    fun createShortCut(
        id: String, shortlabel: String,
        longlabel: String, @DrawableRes resId: Int,
        disableMessage: String, pendingIntent: Intent?
    ) {

        mContext = ZKBase.context()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return
        }
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(mContext, "id不可为空", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(shortlabel)) {
            Toast.makeText(mContext, "shortlabel不可为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(shortlabel)) {
            Toast.makeText(mContext, "longlabel不可为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(disableMessage)) {
            Toast.makeText(mContext, "disableMessage不可为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (pendingIntent == null) {
            Toast.makeText(mContext, "pendingIntent不可为空", Toast.LENGTH_SHORT).show()
            return
        }
        shortcutManager = mContext.getSystemService(ShortcutManager::class.java)
        val shortcut = ShortcutInfo.Builder(mContext, id)
            .setShortLabel(shortlabel)
            .setDisabledMessage(disableMessage)
            .setLongLabel(longlabel)
            .setIcon(Icon.createWithResource(mContext, resId))//可选
            .setIntent(pendingIntent)//必须有action,new Intent(Intent.ACTION_VIEW, Uri.parse("http://zkteam.cc/")
            .build()
        if (shortcutManager!!.dynamicShortcuts.size + shortcutManager!!.manifestShortcuts.size < shortcutManager!!.maxShortcutCountPerActivity) {
            shortcutManager!!.addDynamicShortcuts(Arrays.asList(shortcut))
        } else {
            Toast.makeText(mContext, "不能再添加", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 动态禁用
     *
     * @param id
     */
    fun disableShortCut(id: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return
        }
        if (shortcutManager == null)
            shortcutManager = mContext.getSystemService(ShortcutManager::class.java)
        shortcutManager!!.disableShortcuts(Arrays.asList(id))

    }
}