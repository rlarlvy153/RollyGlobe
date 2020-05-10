package com.rollyglobe.rollyglobe

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan(family:String, type:Typeface): TypefaceSpan(family) {
    val newType:Typeface
    init {
        newType = type
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
    }

    override fun updateMeasureState(paint: TextPaint) {
        super.updateMeasureState(paint)
    }
    private fun applyCustomTypeface(paint : Paint, tf : Typeface) {
        val oldStyle: Int
        val old = paint.typeface
        oldStyle = old?.style ?: 0

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}