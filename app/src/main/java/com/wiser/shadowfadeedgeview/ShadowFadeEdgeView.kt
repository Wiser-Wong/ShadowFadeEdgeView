package com.wiser.shadowfadeedgeview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @author Wiser
 *
 *      上下左右分可带阴影渐变的控件容器
 */
class ShadowFadeEdgeView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val mLeftPaint = Paint()

    private val mTopPaint = Paint()

    private val mRightPaint = Paint()

    private val mBottomPaint = Paint()

    private val mRect = RectF()

    /**
     * 融合器
     */
    private val xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    /**
     * 左阴影渐变
     */
    private var linearGradientLeft: LinearGradient? = null

    /**
     * 上阴影渐变
     */
    private var linearGradientTop: LinearGradient? = null

    /**
     * 右阴影渐变
     */
    private var linearGradientRight: LinearGradient? = null

    /**
     * 下阴影渐变
     */
    private var linearGradientBottom: LinearGradient? = null

    private var layerId: Int = 0

    /**
     * 左阴影边距
     */
    private var shadowFadeLengthLeft = dp2px(50f)

    /**
     * 上阴影边距
     */
    private var shadowFadeLengthTop = dp2px(50f)

    /**
     * 右阴影边距
     */
    private var shadowFadeLengthRight = dp2px(50f)

    /**
     * 下阴影边距
     */
    private var shadowFadeLengthBottom = dp2px(50f)

    /**
     * 是否左阴影方向
     */
    private var isLeftShadowDirection = false

    /**
     * 是否上阴影方向
     */
    private var isTopShadowDirection = false

    /**
     * 是否右阴影方向
     */
    private var isRightShadowDirection = false

    /**
     * 是否下阴影方向
     */
    private var isBottomShadowDirection = false

    init {
        // 添加该方法onDraw会执行
        setWillNotDraw(false)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowFadeEdgeView)
        isLeftShadowDirection = ta.getBoolean(
            R.styleable.ShadowFadeEdgeView_sfev_is_left_fade_direction,
            isLeftShadowDirection
        )
        isTopShadowDirection = ta.getBoolean(
            R.styleable.ShadowFadeEdgeView_sfev_is_top_fade_direction,
            isTopShadowDirection
        )
        isRightShadowDirection = ta.getBoolean(
            R.styleable.ShadowFadeEdgeView_sfev_is_right_fade_direction,
            isRightShadowDirection
        )
        isBottomShadowDirection = ta.getBoolean(
            R.styleable.ShadowFadeEdgeView_sfev_is_bottom_fade_direction,
            isBottomShadowDirection
        )
        shadowFadeLengthLeft =
            ta.getDimension(
                R.styleable.ShadowFadeEdgeView_sfev_left_fade_length,
                shadowFadeLengthLeft
            )
        shadowFadeLengthTop =
            ta.getDimension(
                R.styleable.ShadowFadeEdgeView_sfev_top_fade_length,
                shadowFadeLengthTop
            )
        shadowFadeLengthRight =
            ta.getDimension(
                R.styleable.ShadowFadeEdgeView_sfev_right_fade_length,
                shadowFadeLengthRight
            )
        shadowFadeLengthBottom =
            ta.getDimension(
                R.styleable.ShadowFadeEdgeView_sfev_bottom_fade_length,
                shadowFadeLengthBottom
            )
        ta.recycle()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        layerId = canvas?.saveLayer(mRect, mLeftPaint) ?: 0
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)

        if (isLeftShadowDirection) {
            mLeftPaint.xfermode = xfermode
            mLeftPaint.shader = linearGradientLeft
            canvas?.drawRect(mRect, mLeftPaint)
            mLeftPaint.xfermode = null
        }
        if (isTopShadowDirection) {
            mTopPaint.xfermode = xfermode
            mTopPaint.shader = linearGradientTop
            canvas?.drawRect(mRect, mTopPaint)
            mTopPaint.xfermode = null
        }
        if (isRightShadowDirection) {
            mRightPaint.xfermode = xfermode
            mRightPaint.shader = linearGradientRight
            canvas?.drawRect(mRect, mRightPaint)
            mRightPaint.xfermode = null
        }
        if (isBottomShadowDirection) {
            mBottomPaint.xfermode = xfermode
            mBottomPaint.shader = linearGradientBottom
            canvas?.drawRect(mRect, mBottomPaint)
            mBottomPaint.xfermode = null
        }

        canvas?.restoreToCount(layerId)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRect.left = 0f
        mRect.right = width.toFloat()
        mRect.top = 0f
        mRect.bottom = height.toFloat()

        // 创造一个颜色渐变
//        linearGradient = LinearGradient(
//            if (shadowDirection == SHADOW_RIGHT) width - shadowFadeLength else 0f,
//            if (shadowDirection == SHADOW_BOTTOM) height - shadowFadeLength else 0f,
//            if (shadowDirection == SHADOW_LEFT) shadowFadeLength else if (shadowDirection == SHADOW_RIGHT) width.toFloat() else 0f,
//            if (shadowDirection == SHADOW_TOP) shadowFadeLength else if (shadowDirection == SHADOW_BOTTOM) height.toFloat() else 0f,
//            intArrayOf(
//                if (shadowDirection == SHADOW_BOTTOM || shadowDirection == SHADOW_RIGHT) Color.WHITE else 0,
//                if (shadowDirection == SHADOW_TOP || shadowDirection == SHADOW_LEFT) Color.WHITE else 0
//            ),
//            null,
//            Shader.TileMode.CLAMP
//        )

        if (isLeftShadowDirection) {
            linearGradientLeft = LinearGradient(
                0f,
                0f,
                shadowFadeLengthLeft,
                0f,
                intArrayOf(0, Color.WHITE),
                null,
                Shader.TileMode.CLAMP
            )
        }
        if (isTopShadowDirection) {
            linearGradientTop = LinearGradient(
                0f,
                0f,
                0f,
                shadowFadeLengthTop,
                intArrayOf(0, Color.WHITE),
                null,
                Shader.TileMode.CLAMP
            )
        }
        if (isRightShadowDirection) {
            linearGradientRight = LinearGradient(
                width - shadowFadeLengthRight,
                0f,
                width.toFloat(),
                0f,
                intArrayOf(Color.WHITE, 0),
                null,
                Shader.TileMode.CLAMP
            )
        }
        if (isBottomShadowDirection) {
            linearGradientBottom = LinearGradient(
                0f,
                height - shadowFadeLengthBottom,
                0f,
                height.toFloat(),
                intArrayOf(Color.WHITE, 0),
                null,
                Shader.TileMode.CLAMP
            )
        }

    }

    fun setLeftShadowFadeLength(length: Float) {
        this.shadowFadeLengthLeft = length
        invalidate()
    }

    fun setTopShadowFadeLength(length: Float) {
        this.shadowFadeLengthTop = length
        invalidate()
    }

    fun setRightShadowFadeLength(length: Float) {
        this.shadowFadeLengthRight = length
        invalidate()
    }

    fun setBottomShadowFadeLength(length: Float) {
        this.shadowFadeLengthBottom = length
        invalidate()
    }

    fun setLeftShadowDirection(isLeftShadowDirection: Boolean) {
        this.isLeftShadowDirection = isLeftShadowDirection
        invalidate()
    }

    fun setTopShadowDirection(isTopShadowDirection: Boolean) {
        this.isTopShadowDirection = isTopShadowDirection
        invalidate()
    }

    fun setRightShadowDirection(isRightShadowDirection: Boolean) {
        this.isRightShadowDirection = isRightShadowDirection
        invalidate()
    }

    fun setBottomShadowDirection(isBottomShadowDirection: Boolean) {
        this.isBottomShadowDirection = isBottomShadowDirection
        invalidate()
    }

    private fun dp2px(dpValue: Float): Float {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toFloat()
    }
}