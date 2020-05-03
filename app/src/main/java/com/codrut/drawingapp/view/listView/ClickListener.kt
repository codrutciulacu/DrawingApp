package com.codrut.drawingapp.view.listView

import android.view.View

interface ClickListener {
    fun onClick(view: View, position: Int)
    fun onLongClick(view: View, position: Int)
}