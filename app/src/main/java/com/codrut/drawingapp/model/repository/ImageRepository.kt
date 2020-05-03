package com.codrut.drawingapp.model.repository

import android.util.Log
import com.codrut.drawingapp.model.domain.Drawing
import com.codrut.drawingapp.model.listener.ImageResponseListener
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "Image Repository"

class ImageRepository {

    private val storage by lazy {
        FirebaseFirestore.getInstance()
    }

    fun create(imageName: String, imageResponseListener: ImageResponseListener) {
        val drawing = Drawing(imageName, "")
        storage.collection("images")
            .add(drawing)
            .addOnCompleteListener {
                Log.d(TAG, "The images is uploaded")
                if (it.isSuccessful) {
                    imageResponseListener.getImageId(it.result!!.id)
                }
            }
            .addOnFailureListener { e -> Log.e(TAG, "Error writing document", e) }
    }

    fun update(encodedImage: String?) {
        storage.collection("images")
            .document("img1")
            .update(mapOf<String, String>("content" to encodedImage!!))
    }

    fun getAll(imageResponseListener: ImageResponseListener) {
        storage.collection("images")
            .addSnapshotListener { snapshot, exception ->
                if (exception == null) {
                    val images: MutableList<Drawing> = ArrayList()

                    if (!snapshot!!.isEmpty()) {
                        val documents = snapshot.documents
                        documents.forEach { document ->
                            val drawing: Drawing = document.toObject(Drawing::class.java)!!

                            images.add(drawing)
                        }
                        imageResponseListener.getAllImages(images as List<Drawing>)

                    }
                } else {
                    Log.e(TAG, "Error in getting documents", exception);
                }
            }
    }

    fun get(image: String, imageResponseListener: ImageResponseListener) {
        storage.collection("images").document("img1").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val drawing: Drawing = it.result!!.toObject(Drawing::class.java)!!
                    imageResponseListener.getImage(drawing)
                }
            }
    }
}
