package com.codrut.drawingapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codrut.drawingapp.R
import kotlinx.android.synthetic.main.activity_main.*

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
