package com.codrut.drawingapp.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.codrut.drawingapp.R
import com.codrut.drawingapp.view.fragment.DisplayFragment
import com.codrut.drawingapp.viewModel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*

public const val IMAGE_FIREBASE_ID = "imageFirebaseId"

class MainActivity : AppCompatActivity() {

    private lateinit var imageViewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel::class.java)

        displayFragment(DisplayFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        else -> super.onOptionsItemSelected(item)
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}
