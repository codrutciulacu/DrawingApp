package com.codrut.drawingapp.model.listener

import com.codrut.drawingapp.model.domain.Drawing

interface ImageResponseListener {
    fun getImage(image: Drawing)
    fun getImageId(id: String)
    fun getAllImages(images: List<Drawing>)
}