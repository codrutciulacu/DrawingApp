package com.codrut.drawingapp.viewModel

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codrut.drawingapp.model.domain.Drawing
import com.codrut.drawingapp.model.repository.ImageRepository
import com.codrut.drawingapp.model.listener.ImageResponseListener
import com.codrut.drawingapp.utils.ImageEncoder
import java.util.stream.Collectors

class ImageViewModel : ViewModel(),
    ImageResponseListener {
    private val imageLiveData = MutableLiveData<Drawing>()
    private val imageListLiveData = MutableLiveData<List<Drawing>>()
    private val idLiveData = MutableLiveData<String>()

    private val imageRepository: ImageRepository by lazy {
        ImageRepository()
    }

    public fun update(bitmap: Bitmap) : LiveData<String> {
        val image = ImageEncoder.encodeImage(bitmap)

        imageRepository.update(image)

        val liveData = MutableLiveData<String>()
        liveData.value = image

        return liveData
    }

    override fun getImage(image: Drawing) {
        imageLiveData.postValue(image)
    }

    public fun get(image: String): LiveData<Drawing> {
        imageRepository.get(image, this@ImageViewModel)

        return imageLiveData
    }

    public fun getImages(): LiveData<List<Drawing>>{
        imageRepository.getAll(this@ImageViewModel)

        return imageListLiveData
    }

    override fun getImageId(id: String) {
        idLiveData.postValue(id)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getAllImages(images: List<Drawing>) {
        imageListLiveData.postValue(images)
    }

    fun create(imageName: String): LiveData<String> {
        imageRepository.create(imageName, this@ImageViewModel)

        return idLiveData
    }
}