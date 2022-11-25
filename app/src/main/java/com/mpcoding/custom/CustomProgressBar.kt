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
import com.mpcoding.custom.datamodel.CustomPaintObj
import com.mpcoding.custom.datamodel.CustomVerticalIndicator
import com.mpcoding.customprogressbar.R

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyle: Int = 0
) : View(context, attributes, defStyle) {

    var percentageList: List<CustomVerticalIndicator> = emptyList()
    var startAndStopArrayList: MutableList<CustomPaintObj> = mutableListOf()

    private var startX = 50F
    private var stopX = 900F
    private var startY = 100f
    private var stopY = 100f

    private var count: Int = 0
    private var parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.black)
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 70f
    }
    private var arcPaint = Paint().apply {
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
        for (i in 0 until percentageList.size) {
            startAndStopArrayList.add(CustomPaintObj(50f, 50f))
        }
        animateProgress1(
            percentageList.first().startPosition,
            percentageList.first().stopPosition
        )
        invalidate()
    }

    /**
     * An override method to draw our paint objects in the canvas object
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawLine(it)
            for (i in percentageList.indices) {
                drawThirdLine(it, percentageList.size - 1 - i)
            }
        }
    }


    private fun drawSecondLine(it: Canvas) {
        Log.d("priyadrawInnerArc", stopX.toString())
        val paint = arcPaint
        paint.color = percentageList[1].color!!
        Log.d("priyadrawSecondLine", paint.color.toString())
        it.drawLine(
            startAndStopArrayList[1].startPosition,
            startY,
            startAndStopArrayList[1].stopPosition,
            stopY, paint
        )
    }

    private fun drawThirdLine(it: Canvas, position: Int) {
        Log.d("priyadrawInnerArc", stopX.toString())
        val paint = arcPaint
        paint.color = percentageList[position].color!!
        Log.d("priyadrawThirdLine", paint.color.toString())
        it.drawLine(
            startAndStopArrayList[position].startPosition,
            startY,
            startAndStopArrayList[position].stopPosition,
            stopY, paint
        )
    }

    private fun drawFirstLine(it: Canvas, pausedParentArcPaint: Paint) {
        val paint = arcPaint
        paint.color = percentageList[0].color!!
        Log.d("priyadrawFirstLine", paint.color.toString())
        it.drawLine(
            startAndStopArrayList[0].startPosition,
            startY,
            startAndStopArrayList[0].stopPosition,
            stopY, paint
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
                startAndStopArrayList[count].stopPosition = it.getAnimatedValue("percent") as Float
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
            stopX,
            stopY,
            parentArcPaint
        )
    }
}