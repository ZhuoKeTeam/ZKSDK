package com.zkteam.sdk.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.Utils

/**
 * 剪贴板相关工具类
 */
class ClipboardUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 复制文本到剪贴板
         *
         * @param text 文本
         */
        fun copyText(text: CharSequence) {
            val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            cm.primaryClip = ClipData.newPlainText("text", text)
        }

        /**
         * 获取剪贴板的文本
         *
         * @return 剪贴板的文本
         */
        val text: CharSequence?
            get() {
                val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = cm.primaryClip
                return if (clip != null && clip.itemCount > 0) {
                    clip.getItemAt(0).coerceToText(Utils.getApp())
                } else null
            }

        /**
         * 复制uri到剪贴板
         *
         * @param uri uri
         */
        fun copyUri(uri: Uri) {
            val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            cm.primaryClip = ClipData.newUri(Utils.getApp().contentResolver, "uri", uri)
        }

        /**
         * 获取剪贴板的uri
         *
         * @return 剪贴板的uri
         */
        val uri: Uri?
            get() {
                val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = cm.primaryClip
                return if (clip != null && clip.itemCount > 0) {
                    clip.getItemAt(0).uri
                } else null
            }

        /**
         * 复制意图到剪贴板
         *
         * @param intent 意图
         */
        fun copyIntent(intent: Intent) {
            val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            cm.primaryClip = ClipData.newIntent("intent", intent)
        }

        /**
         * 获取剪贴板的意图
         *
         * @return 剪贴板的意图
         */
        val intent: Intent?
            get() {
                val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = cm.primaryClip
                return if (clip != null && clip.itemCount > 0) {
                    clip.getItemAt(0).intent
                } else null
            }
    }
}
