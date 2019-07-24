package com.zkteam.sdk.swipepanel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.NonNull
import com.zkteam.sdk.R


@Suppress("DEPRECATION")
open class ZKSwipePanelX : FrameLayout {

    companion object {
        const val TAG = "SwipePanel"

        const val LEFT = 0
        const val TOP = 1
        const val RIGHT = 2
        const val BOTTOM = 3
    }

    @IntDef(LEFT, TOP, RIGHT, BOTTOM)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Direction

    private val TRIGGER_PROGRESS = 0.95f

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mPaint: Paint

    private var halfSize: Float = 0.0f
    private var unit: Float = 0.0f

    private var mTouchSlop: Int = 0

    private val mPath = arrayOfNulls<Path>(4)
    private val mPaintColor = IntArray(4)
    private val mEdgeSizes = IntArray(4)
    private val mDrawables = arrayOfNulls<Drawable>(4)
    private val mBitmaps = arrayOfNulls<Bitmap>(4)
    private val mIsStart = BooleanArray(4)
    private val mDown = FloatArray(4)
    private val progresses = FloatArray(4)
    private val preProgresses = FloatArray(4)
    private val mCloses = BooleanArray(4)
    private val mStartSpeed = FloatArray(4)
    private val mIsCenter = BooleanArray(4)
    private val mEnabled = booleanArrayOf(true, true, true, true)

    private var mDownX: Float = 0.toFloat()
    private var mDownY: Float = 0.toFloat()
    private var mCurrentX: Float = 0.toFloat()
    private var mCurrentY: Float = 0.toFloat()
    private val mRectF = RectF()

    private var mIsEdgeStart: Boolean = false
    private var mStartDirection = -1

    private var mLimit: Int = 0

    private var mListener: OnFullSwipeListener? = null


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val edgeSlop = ViewConfiguration.get(context).scaledEdgeSlop
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mPaint.style = Paint.Style.FILL

        halfSize = dp2px(72f).toFloat()
        unit = halfSize / 16

        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.SwipePanel)

            setLeftSwipeColor(ta.getColor(R.styleable.SwipePanel_leftSwipeColor, Color.BLACK))
            setTopSwipeColor(ta.getColor(R.styleable.SwipePanel_topSwipeColor, Color.BLACK))
            setRightSwipeColor(ta.getColor(R.styleable.SwipePanel_rightSwipeColor, Color.BLACK))
            setBottomSwipeColor(ta.getColor(R.styleable.SwipePanel_bottomSwipeColor, Color.BLACK))

            setLeftEdgeSize(ta.getDimensionPixelSize(R.styleable.SwipePanel_leftEdgeSize, edgeSlop))
            setTopEdgeSize(ta.getDimensionPixelSize(R.styleable.SwipePanel_topEdgeSize, edgeSlop))
            setRightEdgeSize(ta.getDimensionPixelSize(R.styleable.SwipePanel_rightEdgeSize, edgeSlop))
            setBottomEdgeSize(ta.getDimensionPixelSize(R.styleable.SwipePanel_bottomEdgeSize, edgeSlop))

            setLeftDrawable(ta.getDrawable(R.styleable.SwipePanel_leftDrawable))
            setTopDrawable(ta.getDrawable(R.styleable.SwipePanel_topDrawable))
            setRightDrawable(ta.getDrawable(R.styleable.SwipePanel_rightDrawable))
            setBottomDrawable(ta.getDrawable(R.styleable.SwipePanel_bottomDrawable))

            setLeftCenter(ta.getBoolean(R.styleable.SwipePanel_isLeftCenter, false))
            setTopCenter(ta.getBoolean(R.styleable.SwipePanel_isTopCenter, false))
            setRightCenter(ta.getBoolean(R.styleable.SwipePanel_isRightCenter, false))
            setBottomCenter(ta.getBoolean(R.styleable.SwipePanel_isBottomCenter, false))

            setLeftEnabled(ta.getBoolean(R.styleable.SwipePanel_isLeftEnabled, true))
            setTopEnabled(ta.getBoolean(R.styleable.SwipePanel_isTopEnabled, true))
            setRightEnabled(ta.getBoolean(R.styleable.SwipePanel_isRightEnabled, true))
            setBottomEnabled(ta.getBoolean(R.styleable.SwipePanel_isBottomEnabled, true))

            ta.recycle()
        }
    }

    fun setLeftSwipeColor(color: Int) {
        setSwipeColor(color, LEFT)
    }

    fun setTopSwipeColor(color: Int) {
        setSwipeColor(color, TOP)
    }

    fun setRightSwipeColor(color: Int) {
        setSwipeColor(color, RIGHT)
    }

    fun setBottomSwipeColor(color: Int) {
        setSwipeColor(color, BOTTOM)
    }

    private fun setSwipeColor(color: Int, direction: Int) {
        mPaintColor[direction] = color
    }

    fun setLeftEdgeSize(size: Int) {
        mEdgeSizes[LEFT] = size
    }

    fun setTopEdgeSize(size: Int) {
        mEdgeSizes[TOP] = size
    }

    fun setRightEdgeSize(size: Int) {
        mEdgeSizes[RIGHT] = size
    }

    fun setBottomEdgeSize(size: Int) {
        mEdgeSizes[BOTTOM] = size
    }

    fun setLeftDrawable(@DrawableRes drawableId: Int) {
        setDrawable(drawableId, LEFT)
    }

    fun setTopDrawable(@DrawableRes drawableId: Int) {
        setDrawable(drawableId, TOP)
    }

    fun setRightDrawable(@DrawableRes drawableId: Int) {
        setDrawable(drawableId, RIGHT)
    }

    fun setBottomDrawable(@DrawableRes drawableId: Int) {
        setDrawable(drawableId, BOTTOM)
    }

    private fun setDrawable(drawableId: Int, direction: Int) {
        mDrawables[direction] = getDrawable(context, drawableId)
    }

    fun setLeftDrawable(drawable: Drawable?) {
        setDrawable(drawable, LEFT)
    }

    fun setTopDrawable(drawable: Drawable?) {
        setDrawable(drawable, TOP)
    }

    fun setRightDrawable(drawable: Drawable?) {
        setDrawable(drawable, RIGHT)
    }

    fun setBottomDrawable(drawable: Drawable?) {
        setDrawable(drawable, BOTTOM)
    }

    private fun setDrawable(drawable: Drawable?, direction: Int) {
        if (drawable == null) return
        mDrawables[direction] = drawable
    }

    fun setLeftCenter(isCenter: Boolean) {
        setCenter(isCenter, LEFT)
    }

    fun setTopCenter(isCenter: Boolean) {
        setCenter(isCenter, TOP)
    }

    fun setRightCenter(isCenter: Boolean) {
        setCenter(isCenter, RIGHT)
    }

    fun setBottomCenter(isCenter: Boolean) {
        setCenter(isCenter, BOTTOM)
    }

    private fun setCenter(isCenter: Boolean, direction: Int) {
        mIsCenter[direction] = isCenter
    }

    fun setLeftEnabled(enabled: Boolean) {
        setEnabled(enabled, LEFT)
    }

    fun setTopEnabled(enabled: Boolean) {
        setEnabled(enabled, TOP)
    }

    fun setRightEnabled(enabled: Boolean) {
        setEnabled(enabled, RIGHT)
    }

    fun setBottomEnabled(enabled: Boolean) {
        setEnabled(enabled, BOTTOM)
    }

    private fun setEnabled(enabled: Boolean, direction: Int) {
        mEnabled[direction] = enabled
    }

    fun wrapView(@NonNull view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            val i = parent.indexOfChild(view)
            val layoutParams = view.layoutParams
            parent.removeViewAt(i)
            parent.addView(this, i, layoutParams)
            addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        } else {
            addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    fun setOnFullSwipeListener(listener: OnFullSwipeListener) {
        mListener = listener
    }

    fun isOpen(direction: Int): Boolean {
        return progresses[direction] >= TRIGGER_PROGRESS
    }

    fun close() {
        mCloses[LEFT] = true
        mCloses[TOP] = true
        mCloses[RIGHT] = true
        mCloses[BOTTOM] = true
        postInvalidate()
    }

    fun close(@Direction direction: Int) {
        mCloses[direction] = true
        mStartSpeed[direction] = 0f
        postInvalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        mLimit = Math.min(mWidth, mHeight) / 3
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        drawPath(canvas)
        animClose()
    }

    private fun drawPath(canvas: Canvas) {
        drawPath(canvas, LEFT)
        drawPath(canvas, TOP)
        drawPath(canvas, RIGHT)
        drawPath(canvas, BOTTOM)
    }

    private fun drawPath(canvas: Canvas, direction: Int) {
        if (mPath[direction] == null) return
        updatePaint(direction)
        canvas.drawPath(getPath(direction), mPaint)
        drawIcon(canvas, direction)
    }

    private fun getPath(direction: Int): Path {
        if (preProgresses[direction] != progresses[direction]) {
            mPath[direction]!!.reset()
            val edge: Float
            val pivot = mDown[direction]
            val mark: Int
            if (direction == LEFT) {
                edge = 0f
                mark = 1
            } else if (direction == TOP) {
                edge = 0f
                mark = 1
            } else if (direction == RIGHT) {
                edge = mWidth.toFloat()
                mark = -1
            } else {
                edge = mHeight.toFloat()
                mark = -1
            }
            if (direction == LEFT || direction == RIGHT) {
                curPathX = edge
                curPathY = pivot - halfSize
            } else {
                curPathX = pivot - halfSize
                curPathY = edge
            }
            mPath[direction]!!.moveTo(curPathX, curPathY)

            quad(edge, pivot - halfSize, direction)
            quad(edge + progresses[direction] * unit * mark.toFloat(), pivot - halfSize + 5 * unit, direction)// 1, 5
            quad(edge + progresses[direction] * 10f * unit * mark.toFloat(), pivot, direction)// 10, 16
            quad(edge + progresses[direction] * unit * mark.toFloat(), pivot + halfSize - 5 * unit, direction)
            quad(edge, pivot + halfSize, direction)
            quad(edge, pivot + halfSize, direction)
        }
        return mPath[direction]!!
    }

    private fun drawIcon(canvas: Canvas, direction: Int) {
        if (mDrawables[direction] == null) return
        if (mBitmaps[direction] == null || mBitmaps[direction]!!.isRecycled) {
            mBitmaps[direction] = drawable2Bitmap(mDrawables[direction]!!)
        }
        if (mBitmaps[direction] == null || mBitmaps[direction]!!.isRecycled) {
            Log.e(TAG, "couldn't get bitmap.")
            return
        }
        val bitmapWidth = mBitmaps[direction]!!.width.toFloat()
        val bitmapHeight = mBitmaps[direction]!!.height.toFloat()
        val fitSize = (progresses[direction] * 5f * unit).toInt().toFloat()

        val width: Float
        val height: Float
        var deltaWidth = 0f
        var deltaHeight = 0f

        if (bitmapWidth >= bitmapHeight) {
            width = fitSize
            height = width * bitmapHeight / bitmapWidth
            deltaHeight = fitSize - height
        } else {
            height = fitSize
            width = height * bitmapWidth / bitmapHeight
            deltaWidth = fitSize - width
        }

        if (direction == LEFT) {
            mRectF.left = 0f + progresses[direction] * unit * 1f + deltaWidth / 2 * 1
            mRectF.top = mDown[LEFT] - height / 2
            mRectF.right = mRectF.left + width
            mRectF.bottom = mRectF.top + height
        } else if (direction == RIGHT) {
            mRectF.right = mWidth.toFloat() + progresses[direction] * unit * -1f + deltaWidth / 2 * -1
            mRectF.top = mDown[RIGHT] - height / 2f
            mRectF.left = mRectF.right - width
            mRectF.bottom = mRectF.top + height
        } else if (direction == TOP) {
            mRectF.left = mDown[TOP] - width / 2
            mRectF.top = 0f + progresses[direction] * unit * 1f + deltaHeight / 2 * 1
            mRectF.right = mRectF.left + width
            mRectF.bottom = mRectF.top + height
        } else {
            mRectF.left = mDown[BOTTOM] - width / 2
            mRectF.bottom = mHeight.toFloat() + progresses[direction] * unit * -1f + deltaHeight / 2 * -1
            mRectF.top = mRectF.bottom - height
            mRectF.right = mRectF.left + width
        }
        canvas.drawBitmap(mBitmaps[direction]!!, null, mRectF, null)
    }

    private fun quad(pathX: Float, pathY: Float, direction: Int) {
        val preX = curPathX
        val preY = curPathY
        if (direction == LEFT || direction == RIGHT) {
            curPathX = pathX
            curPathY = pathY
        } else {

            curPathX = pathY

            curPathY = pathX
        }
        mPath[direction]!!.quadTo(preX, preY, (preX + curPathX) / 2, (preY + curPathY) / 2)
    }

    private var curPathX: Float = 0.toFloat()
    private var curPathY: Float = 0.toFloat()

    private fun updatePaint(direction: Int) {
        mPaint.color = mPaintColor[direction]
        var alphaProgress = progresses[direction]
        if (alphaProgress < 0.25f) {
            alphaProgress = 0.25f
        } else if (alphaProgress > 0.75f) {
            alphaProgress = 0.75f
        }
        mPaint.alpha = (alphaProgress * 255).toInt()
    }

    private fun animClose() {
        val l = animClose(LEFT)
        val u = animClose(TOP)
        val r = animClose(RIGHT)
        val d = animClose(BOTTOM)
        if (l || u || r || d) {
            postInvalidateDelayed(0)
        }
    }

    private fun animClose(direction: Int): Boolean {
        if (mCloses[direction]) {
            if (progresses[direction] > 0) {
                val activity = getActivityByView(this)
                if (activity != null && activity.isFinishing) {
                    progresses[direction] = 0f
                    mCloses[direction] = false
                    return true
                }
                progresses[direction] -= mStartSpeed[direction]
                if (progresses[direction] <= 0) {
                    progresses[direction] = 0f
                    mCloses[direction] = false
                }
                mStartSpeed[direction] += 0.1f
                return true
            }
        }
        return false
    }

    @SuppressLint("WrongConstant")
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        super.dispatchTouchEvent(ev)
        val action = ev.action
        if (action == MotionEvent.ACTION_DOWN) {
            mDownX = ev.x
            mDownY = ev.y
            mIsStart[LEFT] = mEnabled[LEFT] && mDrawables[LEFT] != null && !isOpen(LEFT) && mDownX <= mEdgeSizes[LEFT]
            mIsStart[TOP] = mEnabled[TOP] && mDrawables[TOP] != null && !isOpen(TOP) && mDownY <= mEdgeSizes[TOP]
            mIsStart[RIGHT] =
                mEnabled[RIGHT] && mDrawables[RIGHT] != null && !isOpen(RIGHT) && mDownX >= width - mEdgeSizes[RIGHT]
            mIsStart[BOTTOM] =
                mEnabled[BOTTOM] && mDrawables[BOTTOM] != null && !isOpen(BOTTOM) && mDownY >= height - mEdgeSizes[BOTTOM]
            mIsEdgeStart = mIsStart[LEFT] || mIsStart[TOP] || mIsStart[RIGHT] || mIsStart[BOTTOM]
            if (mIsEdgeStart) {
                mStartDirection = -1
            }
            return true
        }
        if (mIsEdgeStart) {
            if (action == MotionEvent.ACTION_MOVE) {
                mCurrentX = ev.x
                mCurrentY = ev.y
                if (mStartDirection == -1) {
                    val deltaX = mCurrentX - mDownX
                    val deltaY = mCurrentY - mDownY
                    val disX = Math.abs(deltaX)
                    val disY = Math.abs(deltaY)
                    if (disX > mTouchSlop || disY > mTouchSlop) {
                        if (disX >= disY) {
                            if (mIsStart[LEFT] && deltaX > 0) {
                                decideDirection(LEFT)
                            } else if (mIsStart[RIGHT] && deltaX < 0) {
                                decideDirection(RIGHT)
                            }
                        } else {
                            if (mIsStart[TOP] && deltaY > 0) {
                                decideDirection(TOP)
                            } else if (mIsStart[BOTTOM] && deltaY < 0) {
                                decideDirection(BOTTOM)
                            }
                        }
                    }
                }
                if (mStartDirection != -1) {
                    val preProgress = preProgresses[mStartDirection]
                    preProgresses[mStartDirection] = progresses[mStartDirection]
                    progresses[mStartDirection] = calculateProgress()
                    if (Math.abs(preProgress - progresses[mStartDirection]) > 0.01) {
                        postInvalidate()
                    } else {
                        preProgresses[mStartDirection] = preProgress
                    }
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                if (mStartDirection != -1) {
                    mCurrentX = ev.x
                    mCurrentY = ev.y
                    progresses[mStartDirection] = calculateProgress()
                    if (isOpen(mStartDirection)) {
                        if (mListener != null) {
                            mListener!!.onFullSwipe(mStartDirection)
                        }
                    } else {
                        close(mStartDirection)
                    }
                }
            }
        }
        return true
    }

    private fun decideDirection(direction: Int) {
        if (direction == LEFT || direction == RIGHT) {
            if (mIsCenter[direction]) {
                mDown[direction] = mHeight / 2f
            } else {
                if (mDownY < halfSize) {
                    mDown[direction] = halfSize
                } else if (mDownY >= mHeight - halfSize) {
                    mDown[direction] = mHeight - halfSize
                } else {
                    mDown[direction] = mDownY
                }
            }
        } else {
            if (mIsCenter[direction]) {
                mDown[direction] = mWidth / 2f
            } else {
                if (mDownX < halfSize) {
                    mDown[direction] = halfSize
                } else if (mDownX >= mWidth - halfSize) {
                    mDown[direction] = mWidth - halfSize
                } else {
                    mDown[direction] = mDownX
                }
            }
        }
        mStartDirection = direction
        if (mPath[direction] == null) {
            mPath[direction] = Path()
        }
        preProgresses[direction] = 0f
        cancelChildViewTouch()
        requestDisallowInterceptTouchEvent(true)
    }

    private fun calculateProgress(): Float {
        if (mStartDirection == LEFT) {
            val deltaX = mCurrentX - mDownX
            return if (deltaX <= 0) 0f else Math.min(deltaX / mLimit, 1f)
        } else if (mStartDirection == TOP) {
            val deltaY = mCurrentY - mDownY
            return if (deltaY <= 0) 0f else Math.min(deltaY / mLimit, 1f)
        } else if (mStartDirection == RIGHT) {
            val deltaX = mCurrentX - mDownX
            return if (deltaX >= 0) 0f else Math.min(-deltaX / mLimit, 1f)
        } else {
            val deltaY = mCurrentY - mDownY
            return if (deltaY >= 0) 0f else Math.min(-deltaY / mLimit, 1f)
        }
    }

    private fun cancelChildViewTouch() {
        val now = SystemClock.uptimeMillis()
        val cancelEvent = MotionEvent.obtain(
            now, now,
            MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0
        )
        val childCount = childCount
        for (i in 0 until childCount) {
            getChildAt(i).dispatchTouchEvent(cancelEvent)
        }
        cancelEvent.recycle()
    }

    private fun getActivityByView(@NonNull view: View): Activity? {
        return getActivityByContext(view.context)
    }

    private fun getActivityByContext(@NonNull context: Context): Activity? {
        var mContext = context
        while (mContext is ContextWrapper) {
            if (mContext is Activity) {
                return mContext
            }
            mContext = mContext.baseContext
        }
        return null
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(
                1, 1,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565
            )
        } else {
            bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private val sLock = Any()

    private var sTempValue: TypedValue? = null

    private fun getDrawable(@NonNull context: Context, @DrawableRes id: Int): Drawable? {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(id)
        } else if (Build.VERSION.SDK_INT >= 16) {
            return context.resources.getDrawable(id)
        } else {
            val resolvedId: Int
            synchronized(sLock) {
                if (sTempValue == null) {
                    sTempValue = TypedValue()
                }
                context.resources.getValue(id, sTempValue, true)
                resolvedId = sTempValue!!.resourceId
            }
            return context.resources.getDrawable(resolvedId)
        }
    }

    public interface OnFullSwipeListener {
        fun onFullSwipe(@Direction direction: Int)
    }
}