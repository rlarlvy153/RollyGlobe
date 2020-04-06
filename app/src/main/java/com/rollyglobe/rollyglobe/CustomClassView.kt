package com.rollyglobe.rollyglobe

import android.content.Context
import android.text.method.Touch.onTouchEvent
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import timber.log.Timber


class CustomScrollView : ScrollView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN ->{
                Timber.d("action down")
                super.onTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                Timber.d("action move")

                return false // redirect MotionEvents to ourself
            }
            MotionEvent.ACTION_CANCEL ->{
                Timber.d("action cancle")
                // Log.i("CustomScrollView", "onInterceptTouchEvent: CANCEL super false" );
                super.onTouchEvent(ev)
            }
            MotionEvent.ACTION_UP ->{
                Timber.d("action up")
                //Log.i("CustomScrollView", "onInterceptTouchEvent: UP super false" );
                return false
            }
            else -> {
            }
        }//Log.i("CustomScrollView", "onInterceptTouchEvent: " + action );

        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        super.onTouchEvent(ev)
        //Log.i("CustomScrollView", "onTouchEvent. action: " + ev.getAction() );
        return true
    }
}