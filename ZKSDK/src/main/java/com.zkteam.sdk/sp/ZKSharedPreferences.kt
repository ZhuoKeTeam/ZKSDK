package com.zkteam.sdk.sp

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.annotation.CallSuper
import com.zkteam.sdk.ZKBase
import com.zkteam.sdk.exception.ZKSPException

abstract class ZKSharedPreferences : IZKSharedPreferences {

    var SP_NAME = this::class.java.simpleName
    var KEY_NAME = this::class.java.name

    private var context: Context? = null
    var spFileName: String

    fun setSPName(spName: String) {
        SP_NAME = spName
    }

    abstract fun sharedPreferencesFileName(): String

    init {
        this.context = ZKBase.context()
        this.spFileName = this.sharedPreferencesFileName()
    }

    @CallSuper
    @Throws(ZKSPException::class)
    override fun put(key: String, defaultObject: Any) {
        check()

        val editor = getEditor()

        try {
            when (defaultObject) {
                is String -> editor.putString(key, defaultObject)
                is Int -> editor.putInt(key, defaultObject)
                is Boolean -> editor.putBoolean(key, defaultObject)
                is Float -> editor.putFloat(key, defaultObject)
                is Long -> editor.putLong(key, defaultObject)
                else -> editor.putString(key, defaultObject.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        saveEditor(editor)
    }

    @Throws(ZKSPException::class)
    override fun get(key: String, defaultObject: Any): Any? {
        check()

        val sp = getSharedPreferences()

        try {
            when (defaultObject) {
                is String -> return sp.getString(key, defaultObject)
                is Int -> return sp.getInt(key, defaultObject)
                is Boolean -> return sp.getBoolean(key, defaultObject)
                is Float -> return sp.getFloat(key, defaultObject)
                is Long -> return sp.getLong(key, defaultObject)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(ZKSPException::class)
    override fun remove(key: String) {
        check()

        val editor = getEditor()
        editor.remove(key)
        saveEditor(editor)
    }

    @Throws(ZKSPException::class)
    override fun clean() {
        check()

        val editor = getEditor()
        editor.clear()
        saveEditor(editor)
    }

    @Throws(ZKSPException::class)
    override fun contain(key: String): Boolean {
        check()

        val sp = getSharedPreferences()
        return sp.contains(key)
    }

    @Throws(ZKSPException::class)
    override fun getAll(): Map<String, *>? {
        check()

        val sp = getSharedPreferences()
        try {
            return sp.all
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 检测 条件是否匹配，如果不匹配 抛出异常
     *
     * @throws ZKSharePreferencesException 自定义异常
     */
    @Throws(ZKSPException::class)
    private fun check() {
        if (context == null)
            throw ZKSPException("Context is null!")

        if (TextUtils.isEmpty(spFileName))
            throw ZKSPException("SharedPreferencesFileName is empty!")
    }

    /**
     * 获取 SharedPreferences
     * @return SharedPreferences
     */
    private fun getSharedPreferences(): SharedPreferences {
        return context!!.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
    }

    /**
     * 获取 SharedPreferences.Editor
     * @return SharedPreferences.Editor
     */
    private fun getEditor(): SharedPreferences.Editor {
        val sp = getSharedPreferences()
        return sp.edit()
    }

    /**
     * 异步保存 editor，推荐使用
     * 如果有特殊需求，请 直接使用 commit()。
     * @param editor editor
     */
    private fun saveEditor(editor: SharedPreferences.Editor) {
        try {
            //            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
            //            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}