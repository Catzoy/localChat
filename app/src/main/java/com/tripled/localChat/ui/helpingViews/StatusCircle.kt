package com.tripled.localChat.ui.helpingViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tripled.localChat.R

class StatusCircle : View {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = Color.TRANSPARENT
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StatusCircle, defStyle, 0)
        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2.0f, height / 2.0f, 10.0f, paint)
    }

    fun setColor(color: Int) {
        paint.color = color
    }
}
