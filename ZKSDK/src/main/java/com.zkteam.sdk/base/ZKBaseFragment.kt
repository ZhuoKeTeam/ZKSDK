package com.zkteam.sdk.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ClickUtils

public abstract class ZKBaseFragment : Fragment(), IZKBaseView {

    private val TAG = "ZKBaseFragment"
    private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"

    private val mClickListener = View.OnClickListener { v -> onDebouncingClick(v) }

    protected lateinit var mActivity: Activity
    protected lateinit var mInflater: LayoutInflater
    protected var mContentView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager!!.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }

            ft.commitAllowingStateLoss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mInflater = inflater
        setRootLayout(getLayoutId())
        return mContentView
    }

    @SuppressLint("ResourceType")
    fun setRootLayout(@LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = mInflater.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        initData(bundle!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews(mContentView!!)
        initListener()
        initLifecycleObserve()
        initData(savedInstanceState)
    }

    override fun onDestroyView() {
        if (mContentView != null) {
            (mContentView!!.parent as ViewGroup).removeView(mContentView)
        }
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    fun applyDebouncingClickListener(vararg views: View) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener)
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        if (mContentView == null) throw NullPointerException("ContentView is null.")
        return mContentView!!.findViewById(id)
    }

}