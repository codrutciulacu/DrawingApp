package com.codrut.drawingapp.view.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Half.toFloat
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.codrut.drawingapp.R
import com.codrut.drawingapp.model.domain.Drawing
import com.codrut.drawingapp.utils.ImageEncoder
import com.codrut.drawingapp.viewModel.ImageViewModel

private const val STROKE_WIDTH = 12f

class CanvasView(context: Context, attr: AttributeSet) : View(context, attr) {

    private lateinit var imageViewModel: ImageViewModel
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private var imageFirebaseId = ""

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.backgroundColor, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.paintColor, null)

    private val paintBrush = Paint().apply {
        color = drawColor

        isAntiAlias = true

        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    private var path = Path()

    private var motionTouchEventX = 0f

    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        imageViewModel = ImageViewModel()

        extraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawBitmap(extraBitmap, 0f, 0f, paintBrush)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun touchUp() {
        imageViewModel.update(imageFirebaseId, extraBitmap)
        path.reset()
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)

        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )

            currentX = motionTouchEventX
            currentY = motionTouchEventY

            extraCanvas.drawPath(path, paintBrush)
        }
        invalidate()
    }

    private fun touchDown() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)

        currentX = motionTouchEventX
        currentY = motionTouchEventY

        extraCanvas.drawPoint(motionTouchEventX, motionTouchEventY, paintBrush)
    }

    public fun setImage(image: Drawing) {
        this.imageFirebaseId = image.id

        val decodedImage: Bitmap? = ImageEncoder.decodeImage(image.content)
        if (decodedImage != null) {
            createCanvas(decodedImage)
        } else {
            createCanvas(extraBitmap)
        }
    }

    private fun createCanvas(bitmap: Bitmap) {
        extraBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        extraCanvas = Canvas(extraBitmap)
        draw(extraCanvas)
        invalidate()
    }
}