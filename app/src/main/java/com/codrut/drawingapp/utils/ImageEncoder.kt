package com.codrut.drawingapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import java.io.ByteArrayOutputStream
import java.lang.RuntimeException
import java.util.*


class ImageEncoder {
    companion object {
        @JvmStatic fun encodeImage(extraBitmap: Bitmap): String {
            val byteArray = getBitmapAsByteArray(extraBitmap)
            var encodedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(byteArray)
            } else {
                throw RuntimeException("The application requires android O to encode the image")
            }
            return encodedImage;
        }

        @JvmStatic fun decodeImage(image: String): Bitmap? {
            var byteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(image)
            } else {
                throw RuntimeException("The application requires android O to encode the image")
            }

            return BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
        }

        private fun getBitmapAsByteArray(extraBitmap: Bitmap): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            extraBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            return byteArray
        }
    }
}
