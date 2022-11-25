package com.mpcoding.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.animation.doOnEnd
import com.mpcoding.custom.datamodel.CustomVerticalIndicator
import com.mpcoding.customprogressbar.R

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyle: Int = 0
) : View(context, attributes, defStyle) {

    var percentageList: List<CustomVerticalIndicator> = emptyList()

    private var startX = 50F
    private var stopX = 50F
    private var stopX1 = 250F
    private var stopX2 = 50F
    private var stopX3 = 50F
    private var startY = 100f
    private var stopY = 100f
    private var startX2 = 50F
    private var startX3 = 50F

    private var count: Int = 0
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


    fun setPercentage(percentageList: MutableList<CustomVerticalIndicator>) {
        this.percentageList = percentageList
        animateProgress1(percentageList.first().startPosition, percentageList.first().stopPosition)
        invalidate()
    }

    /**
     * An override method to draw our paint objects in the canvas object
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            Log.d("priyaonDraw", "onDraw")
            drawLine(it)
            drawThirdLine(it, pausedParentArcPaint)
            drawSecondLine(it, completedParentArcPaint)
            drawFirstLine(it, skippedArcPaint)
        }
    }


    private fun drawSecondLine(it: Canvas, paint: Paint) {
        Log.d("priyadrawInnerArc", stopX.toString())
        it.drawLine(
            startX2,
            startY,
            stopX2,
            stopY, completedParentArcPaint
        )
    }

    private fun drawThirdLine(it: Canvas, pausedParentArcPaint: Paint) {
        Log.d("priyadrawInnerArc", stopX.toString())
        it.drawLine(
            startX3,
            startY,
            stopX3,
            stopY, pausedParentArcPaint
        )
    }

    private fun drawFirstLine(it: Canvas, pausedParentArcPaint: Paint) {
        Log.d("priyadrawInnerArc", stopX.toString())
        it.drawLine(
            startX,
            startY,
            stopX1,
            stopY, skippedArcPaint
        )
    }

    private fun animateProgress1(startX: Float, stopX: Float) {
        val valuesHolder =
            PropertyValuesHolder.ofFloat(
                "percent",
                startX,
                stopX
            )
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            addUpdateListener {
                when (count) {
                    0 -> {
                        stopX1 = it.getAnimatedValue("percent") as Float
                    }
                    1 -> {
                        stopX2 = it.getAnimatedValue("percent") as Float
                    }
                    2 -> {
                        stopX3 = it.getAnimatedValue("percent") as Float
                    }
                }
                invalidate()
            }
            this.doOnEnd {
                if (count < percentageList.size - 1) {
                    count++
                    animateProgress1(
                        percentageList[count].startPosition,
                        percentageList[count].stopPosition,
                    )
                }
            }
            duration = 800

        }
        animator.start()
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

}