package com.rollyglobe.ui.recommend.inner_contents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.SupportMapFragment
import com.rollyglobe.R


class WorkaroundMapFragment : SupportMapFragment() {

    var mListener: OnTouchListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (view is ViewGroup) {
            val frameLayout = TouchableWrapper(requireContext()).apply {
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
            }

            view.addView(frameLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    fun setListener(listener: OnTouchListener) {
        mListener = listener
    }

    interface OnTouchListener {
        fun onTouch()
    }

    inner class TouchableWrapper(context: Context) : FrameLayout(context) {
        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> mListener?.onTouch()
                MotionEvent.ACTION_UP -> mListener?.onTouch()
            }
            return super.dispatchTouchEvent(event)
        }
    }
}