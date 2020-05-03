package com.codrut.drawingapp.view.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.codrut.drawingapp.R
import com.codrut.drawingapp.view.activity.CanvasActivity
import com.codrut.drawingapp.view.activity.IMAGE_FIREBASE_ID
import com.codrut.drawingapp.view.listView.ClickListener
import com.codrut.drawingapp.view.listView.ImageGridAdapter
import com.codrut.drawingapp.view.listView.RecyclerTouchListener
import com.codrut.drawingapp.viewModel.ImageViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG = "Display Fragment"

class DisplayFragment : Fragment() {

    private val imageViewModel: ImageViewModel by lazy {
        ImageViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        initImageGrid(view)

        view.findViewById<FloatingActionButton>(R.id.add_paint_fab).setOnClickListener {
            openDialog()
        }

        return view
    }

    private fun initImageGrid(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val manager: RecyclerView.LayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = manager
        imageViewModel.getImages().observe(viewLifecycleOwner, Observer { images ->
            recyclerView.adapter = ImageGridAdapter(images, context)

            recyclerView.addOnItemTouchListener(RecyclerTouchListener(context!!, recyclerView, object: ClickListener {
                override fun onClick(view: View, position: Int) {
                    val firebaseId = images.get(position).id
                    val intent = Intent(context!!, CanvasActivity::class.java)
                    intent.putExtra(IMAGE_FIREBASE_ID, firebaseId)
                    startActivity(intent)
                }

                override fun onLongClick(view: View, position: Int) {

                }
            }))
        })
    }


    private fun openDialog() {
        val view: View = layoutInflater.inflate(R.layout.dialog_box_add_drawing, null)
        val alertDialog: AlertDialog = AlertDialog.Builder(context!!)
            .setTitle("Create New Drawing")
            .setView(view)
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            }).setPositiveButton("Add", DialogInterface.OnClickListener { dialog, id ->
                val drawingEditText = view.findViewById<EditText>(R.id.drawing_edit_text)
                var drawingId: String = ""

                imageViewModel.create(drawingEditText.text.toString())
                    .observe(viewLifecycleOwner, Observer {
                        Log.d(TAG, it.id)
                        val intent = Intent(context!!, CanvasActivity::class.java)
                        intent.putExtra(IMAGE_FIREBASE_ID, it.id)
                        startActivity(intent)
                    })
            })
            .create()

        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = DisplayFragment()

    }
}
