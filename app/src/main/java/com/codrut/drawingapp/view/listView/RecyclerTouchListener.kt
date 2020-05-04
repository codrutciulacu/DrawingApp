package com.codrut.drawingapp.view.listView

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class RecyclerTouchListener : RecyclerView.OnItemTouchListener {

    private var clickListener: ClickListener? = null
    private var gestureDetector: GestureDetector? = null

    constructor( context: Context,
                 recyclerView: RecyclerView,
                 clickListener: ClickListener){
        this.clickListener = clickListener

        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child: View = recyclerView.findChildViewUnder(e.x, e.y)!!

                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.getX(), e.getY())
        if(child!=null && clickListener!=null && gestureDetector!!.onTouchEvent(e)){
            clickListener!!.onClick(child, rv.getChildAdapterPosition(child))
        }

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

}
