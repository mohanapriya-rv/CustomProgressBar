package com.mpcoding.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import com.mpcoding.custom.datamodel.CustomPaintObj
import com.mpcoding.custom.datamodel.CustomVerticalIndicator
import com.mpcoding.customprogressbar.R

/**
 *Created by Mohanapriya R
 */
class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyle: Int = 0
) : View(context, attributes, defStyle) {

    var percentageListArray: List<CustomVerticalIndicator> = emptyList()
    var startAndStopArrayList: MutableList<CustomPaintObj> = mutableListOf()

    private var startX = 50F
    private var stopX = 900F
    private var startY = 100f
    private var stopY = 100f

    private var count: Int = 0
    var colorsList: List<Int> = emptyList()

    private var parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = context.resources.getColor(R.color.white)
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

    /**
     * we will be getting percentage array and colors
     * percentageListArray have values of obj
     * startAndStopArrayList array's  size will be the same as percentageListArray and
     * initially start and end position will be 50f
     */
    fun setPercentage(
        percentageList: MutableList<CustomVerticalIndicator>,
        colorsList: MutableList<Int>
    ) {
        this.percentageListArray = percentageList
        this.colorsList = colorsList
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
            for (i in percentageListArray.indices) {
                drawThirdLine(it, percentageListArray.size - 1 - i)
            }
        }
    }


    private fun drawThirdLine(it: Canvas, position: Int) {
        val paint = arcPaint
        paint.color = colorsList[position]
        it.drawLine(
            startAndStopArrayList[position].startPosition,
            startY,
            startAndStopArrayList[position].stopPosition,
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
                /**
                 *for particular selected item updating its stopposition
                 */
                startAndStopArrayList[count].stopPosition = it.getAnimatedValue("percent") as Float
                invalidate()
            }
            this.doOnEnd {
                /**
                 *we are iterating the loop till the end  of percentageListArray
                 */
                if (count < percentageListArray.size - 1) {
                    count++
                    animateProgress1(
                        percentageListArray[count].startPosition,
                        percentageListArray[count].stopPosition,
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