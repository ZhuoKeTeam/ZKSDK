package com.zkteam.sdk.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.ToastUtils
import java.util.*

class ShareUtils {
    fun shareText(context: Context, text: String, title: String) {
        try {
            context.startActivity(Intent.createChooser(IntentUtils.getShareTextIntent(text, true), title))
        } catch (e: Exception) {
            e.printStackTrace()
            sendError(context)
        }

    }

    fun shareImage(context: Context, uri: Uri, title: String) {
        try {
            context.startActivity(Intent.createChooser(IntentUtils.getShareImageIntent(title, uri), title))
        } catch (e: Exception) {
            e.printStackTrace()
            sendError(context)
        }

    }

    fun sendEmail(context: Context, title: String, emailAddress: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$emailAddress"))
            context.startActivity(Intent.createChooser(intent, title))
        } catch (e: Exception) {
            e.printStackTrace()
            sendError(context)
        }
    }

    fun sendMoreImage(context: Context, imageUris: ArrayList<Uri>, title: String) {
        try {
            context.startActivity(Intent.createChooser(IntentUtils.getShareImageIntent(title, imageUris), title))
        } catch (e: Exception) {
            e.printStackTrace()
            sendError(context)
        }

    }

    fun sendError(context: Context) {
        ToastUtils.showShort("分享错误")
    }

}