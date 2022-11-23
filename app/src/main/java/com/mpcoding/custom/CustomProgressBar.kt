package com.mpcoding.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.mpcoding.customprogressbar.R

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyle: Int = 0
) : View(context, attributes, defStyle) {

    var percentageList: List<Int> = emptyList()

    private var startX = 50F
    private var stopX = 50F
    private var startY = 100f
    private var stopY = 100f

    init {
        setUPAttributes()
    }

    private var parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.black)
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 70f
    }
    private var completedParentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.teal_700)
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 70f
    }
    private var pausedParentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.black)
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 70f
    }

    private var skippedArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.teal_200)
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 70f
    }

    private var paintArrayList: List<Paint> =
        listOf(completedParentArcPaint, pausedParentArcPaint, skippedArcPaint)

    fun setPercentage(percentageList: List<Int>) {
        this.percentageList = percentageList
        animateProgress(startX, 200f)
    }

    private fun setUPAttributes() {
    }


    /**
     * An override method to draw our paint objects in the canvas object
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawLine(it)
            /**
             *iterate array in reverse order
             */
            drawInnerArc(it, completedParentArcPaint)
        }
    }


    private fun drawInnerArc(it: Canvas, paint: Paint) {
        it.drawLine(
            startX,
            startY,
            stopX,
            stopY, paint
        )
    }


    private fun drawLine(it: Canvas) {
        it.drawLine(
            startX,
            startY,
            900f,
            stopY,
            parentArcPaint
        )
    }

    private fun animateProgress(
        innerLineStartPosition: Float,
        innerLineEndPosition: Float
    ) {
        val valuesHolder =
            PropertyValuesHolder.ofFloat(
                "percent",
                innerLineStartPosition,
                200f
            )
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            addUpdateListener {
                stopX = it.getAnimatedValue("percent") as Float
                invalidate()
            }
            duration = 3000

        }
        animator.start()
    }
}