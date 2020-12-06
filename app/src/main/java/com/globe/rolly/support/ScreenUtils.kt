package com.globe.rolly.support

import android.content.Context
import android.util.TypedValue

object ScreenUtils {
    fun dpToPixel(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}