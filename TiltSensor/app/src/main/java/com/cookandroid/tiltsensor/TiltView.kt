package com.cookandroid.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()
    private var cX :Float=0f
    private var cY :Float=0f

    private var xCoords:Float = 0f
    private var yCoords:Float = 0f

    init {
        greenPaint.color= Color.GREEN
        blackPaint.style= Paint.Style.STROKE
    }
    fun onSensorEvent(event:SensorEvent){
        yCoords = event.values[0] *200
        xCoords = event.values[1] *200

        invalidate()
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w/2f
        cY = h/2f
    }

    override fun onDraw(canvas:Canvas?){
        canvas?.drawCircle(cX,cY,100f,blackPaint)
        canvas?.drawCircle(xCoords+cX,yCoords+cY,100f,greenPaint)
        canvas?.drawLine(cX -20,cY,cX +20,cY,blackPaint)

        canvas?.drawLine( cX,cY-20,cX,cY+20,blackPaint)
    }
}