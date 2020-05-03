package com.codrut.drawingapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codrut.drawingapp.R
import com.codrut.drawingapp.view.fragment.DisplayFragment
import com.codrut.drawingapp.viewModel.ImageViewModel
import kotlinx.android.synthetic.main.activity_canvas.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import java.lang.RuntimeException

private const val TAG = "Canvas Activity"

class CanvasActivity : AppCompatActivity() {

    private val imageViewModel: ImageViewModel by lazy {
        ViewModelProviders.of(this).get(ImageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val imageFirebaseId: String = if(intent.hasExtra(IMAGE_FIREBASE_ID)) {
            intent.getStringExtra(IMAGE_FIREBASE_ID)!!
        }else{
            Log.d(TAG, "Not good")
            ""
        }

        imageViewModel.get(imageFirebaseId).observe(this, Observer {
            supportActionBar!!.setTitle(it.name)
        })
        canvas_view.setImageFirebaseId(imageFirebaseId)
    }
}
