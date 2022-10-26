package com.gorosheg.facedetector.presentation.camera.face

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View
import androidx.camera.view.PreviewView
import com.google.mlkit.vision.face.Face
import com.gorosheg.facedetector.model.ImageSourceInfo

class FaceView(
    context: Context,
    private val previewView: PreviewView
) : View(context) {

    private var faces: List<Face> = emptyList()
    private var imageSourceInfo: ImageSourceInfo = ImageSourceInfo(10, 10, false)

    private val rectPaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = WIDTH
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawFaceRect(canvas)

    }

    private fun drawFaceRect(canvas: Canvas) {
        val scaleHeight = (previewView.height / imageSourceInfo.height).toFloat()
        val scaleWidth = (previewView.width / imageSourceInfo.width).toFloat()

        for (face in faces) {
            val left =
                if (imageSourceInfo.isImageFlipped) imageSourceInfo.width - face.boundingBox.right.toFloat()
                else face.boundingBox.left.toFloat()

            val right =
                if (imageSourceInfo.isImageFlipped) imageSourceInfo.width - face.boundingBox.left.toFloat()
                else face.boundingBox.right.toFloat()

            canvas.drawRect(
                left * scaleWidth,
                face.boundingBox.top.toFloat() * scaleHeight,
                right * scaleWidth + LACK,
                face.boundingBox.bottom.toFloat() * scaleHeight + LACK,
                rectPaint
            )
        }
    }

    fun invalidateFaceView(detectedFaces: List<Face>, newImageSourceInfo: ImageSourceInfo) {
        faces = detectedFaces
        imageSourceInfo = newImageSourceInfo
        invalidate()
    }

    companion object {
        private const val WIDTH = 8F
        private const val LACK = 150
    }
}