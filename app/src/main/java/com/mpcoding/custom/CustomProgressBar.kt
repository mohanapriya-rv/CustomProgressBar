package com.mpcoding.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
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
    private var stopX1 = 150F
    private var stopX2 = 250F
    private var stopX3 = 50F
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
        invalidate()
    }

    private fun setUPAttributes() {
    }


    /**
     * An override method to draw our paint objects in the canvas object
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            Log.d("priyaonDraw", "onDraw")
            drawLine(it)
            drawSkippedLine(it, skippedArcPaint)
            drawInnerArc(it, completedParentArcPaint)
            drawPauseLine(it, pausedParentArcPaint)
        }
    }


    private fun drawInnerArc(it: Canvas, paint: Paint) {
        Log.d("priyadrawInnerArc", stopX.toString())
        it.drawLine(
            stopX1,
            startY,
            stopX2,
            stopY, completedParentArcPaint
        )
    }

    private fun drawPauseLine(it: Canvas, pausedParentArcPaint: Paint) {
        Log.d("priyadrawInnerArc", stopX.toString())
        it.drawLine(
            stopX2,
            startY,
            stopX2 + 100,
            stopY, pausedParentArcPaint
        )
    }

    private fun drawSkippedLine(it: Canvas, pausedParentArcPaint: Paint) {
        Log.d("priyadrawInnerArc", stopX.toString())
        it.drawLine(
            startX,
            startY,
            stopX1,
            stopY, skippedArcPaint
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
        Log.d("priyaanimateProgress", "priyaanimateProgress")
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