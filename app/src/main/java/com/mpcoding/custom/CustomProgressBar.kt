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
        color = context.resources.getColor(R.color.purple_200)
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
    private var paint: Paint? = null

    fun setPercentage(percentageList: List<Int>) {
        this.percentageList = percentageList
        for (i in 0..percentageList.size - 1) {
            paint = if (i == 0) {
                skippedArcPaint
            } else if (i == 1) {
                pausedParentArcPaint
            } else {
                completedParentArcPaint
            }
            animateProgress(startX, percentageList[i].toFloat())
        }
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
            for (i in percentageList) {
                drawInnerArc(it, paint!!)
            }
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
                innerLineEndPosition
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