package kinopoisk.cinema.presentation.customview

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView

// TODO Необходимо заменить, либо переписать
class ZoomImage : AppCompatImageView, View.OnTouchListener,
    GestureDetector.OnGestureListener {

    private var coordinate: Pair<Float, Float>? = null

    private var mContext: Context? = null
    private var mScaleDetector: ScaleGestureDetector? = null
    private var mGestureDetector: GestureDetector? = null
    private var mMatrix: Matrix? = null
    private var mMatrixValues: FloatArray? = null
    private var mode = IMAGE_STATE_NONE

    private var mSaveScale = SAVE_SCALE_VALUE
    private var mMinScale = MIN_SCALE_VALUE
    private var mMaxScale = MAX_SCALE_VALUE

    private var origWidth = ZERO_VALUE
    private var origHeight = ZERO_VALUE
    private var viewWidth = 0
    private var viewHeight = 0
    private var mLast = PointF()
    private var mStart = PointF()

    constructor(context: Context) : super(context) {
        sharedConstructing(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        sharedConstructing(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    private fun sharedConstructing(context: Context) {
        super.setClickable(true)
        mContext = context
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
        mMatrix = Matrix()
        mMatrixValues = FloatArray(ARRAY_SIZE)
        imageMatrix = mMatrix
        scaleType = ScaleType.MATRIX
        mGestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = IMAGE_STATE_ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val prevScale = mSaveScale
            mSaveScale *= mScaleFactor
            if (mSaveScale > mMaxScale) {
                mSaveScale = mMaxScale
                mScaleFactor = mMaxScale / prevScale
            } else if (mSaveScale < mMinScale) {
                mSaveScale = mMinScale
                mScaleFactor = mMinScale / prevScale
            }
            if (origWidth * mSaveScale <= viewWidth
                || origHeight * mSaveScale <= viewHeight
            ) {
                mMatrix!!.postScale(
                    mScaleFactor, mScaleFactor, viewWidth / 2.toFloat(),
                    viewHeight / 2.toFloat()
                )
            } else {
                mMatrix!!.postScale(
                    mScaleFactor, mScaleFactor,
                    detector.focusX, detector.focusY
                )
            }
            fixTranslation()
            return true
        }
    }

    private fun fitToScreen() {
        mSaveScale = 1f
        val scale: Float
        val drawable = drawable
        if (drawable == null || drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) return
        val imageWidth = drawable.intrinsicWidth
        val imageHeight = drawable.intrinsicHeight
        val scaleX = viewWidth.toFloat() / imageWidth.toFloat()
        val scaleY = viewHeight.toFloat() / imageHeight.toFloat()
        scale = scaleX.coerceAtMost(scaleY)
        mMatrix!!.setScale(scale, scale)

        var redundantYSpace = (viewHeight.toFloat()
                - scale * imageHeight.toFloat())
        var redundantXSpace = (viewWidth.toFloat()
                - scale * imageWidth.toFloat())
        redundantYSpace /= 2.toFloat()
        redundantXSpace /= 2.toFloat()
        mMatrix!!.postTranslate(redundantXSpace, redundantYSpace)
        origWidth = viewWidth - 2 * redundantXSpace
        origHeight = viewHeight - 2 * redundantYSpace
        imageMatrix = mMatrix
    }

    fun fixTranslation() {
        mMatrix!!.getValues(mMatrixValues)
        val transX =
            mMatrixValues!![Matrix.MTRANS_X]
        val transY =
            mMatrixValues!![Matrix.MTRANS_Y]
        val fixTransX = getFixTranslation(transX, viewWidth.toFloat(), origWidth * mSaveScale)
        val fixTransY = getFixTranslation(transY, viewHeight.toFloat(), origHeight * mSaveScale)
        if (fixTransX != ZERO_VALUE || fixTransY != ZERO_VALUE) {
            mMatrix!!.postTranslate(fixTransX, fixTransY)
        } else {
            // ignore
        }
    }

    private fun getFixTranslation(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) {
            minTrans = ZERO_VALUE
            maxTrans = viewSize - contentSize
        } else {
            minTrans = viewSize - contentSize
            maxTrans = ZERO_VALUE
        }
        return when {
            trans < minTrans -> -trans + minTrans
            trans > maxTrans -> -trans + maxTrans
            else -> ZERO_VALUE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (mSaveScale == 1f) {
            fitToScreen()
        } else {
            // ignore
        }
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        mScaleDetector!!.onTouchEvent(event)
        mGestureDetector!!.onTouchEvent(event)
        val currentPoint = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLast.set(currentPoint)
                mStart.set(mLast)
                mode = IMAGE_STATE_DRAG
            }
            MotionEvent.ACTION_MOVE -> if (mode == IMAGE_STATE_DRAG) {
                val dx = currentPoint.x - mLast.x
                val dy = currentPoint.y - mLast.y
                val fixTransX = if (origWidth * mSaveScale < viewWidth.toFloat()) {
                    ZERO_VALUE
                } else {
                    dx
                }
                val fixTransY = if (origHeight * mSaveScale < viewHeight.toFloat()) {
                    ZERO_VALUE
                } else {
                    dy
                }
                mMatrix!!.postTranslate(fixTransX, fixTransY)
                fixTranslation()
                mLast[currentPoint.x] = currentPoint.y
            }
            MotionEvent.ACTION_POINTER_UP -> mode = IMAGE_STATE_NONE
        }
        imageMatrix = mMatrix
        return FALSE_VALUE_EVENT
    }

    override fun onDown(motionEvent: MotionEvent): Boolean =
        FALSE_VALUE_EVENT

    override fun onShowPress(motionEvent: MotionEvent) {
        // ignore
    }

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        coordinate = Pair(motionEvent.x, motionEvent.y)
        return FALSE_VALUE_EVENT
    }


    override fun onScroll(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ): Boolean = FALSE_VALUE_EVENT


    override fun onLongPress(motionEvent: MotionEvent) {
        fitToScreen()
    }

    override fun onFling(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ): Boolean = FALSE_VALUE_EVENT

    private companion object {
        const val IMAGE_STATE_NONE = 0
        const val IMAGE_STATE_DRAG = 1
        const val IMAGE_STATE_ZOOM = 2

        const val MAX_SCALE_VALUE = 4f
        const val MIN_SCALE_VALUE = 1f
        const val SAVE_SCALE_VALUE = 1f

        const val ZERO_VALUE = 0f
        const val ARRAY_SIZE = 9
        const val FALSE_VALUE_EVENT = false
    }
}
