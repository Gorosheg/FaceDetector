package com.gorosheg.facedetector.presentation.camera.Face

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View
import androidx.camera.view.PreviewView
import com.google.mlkit.vision.face.Face
import com.gorosheg.facedetector.model.ImageSourceInfo

class FaceDraw(
    context: Context,
    private val faces: List<Face>,
    private val imageSourceInfo: ImageSourceInfo,
    previewView: PreviewView
) : View(context) {

    private val needToMirror = imageSourceInfo.isImageFlipped

    private val rectPaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 8F
    }

    private val scaleHeight = (previewView.height / imageSourceInfo.height).toFloat()
    private val scaleWidth = (previewView.width / imageSourceInfo.width).toFloat()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        for (face in faces) {
            val left =
                if (needToMirror) imageSourceInfo.width - face.boundingBox.right.toFloat()
                else face.boundingBox.left.toFloat()

            val right =
                if (needToMirror) imageSourceInfo.width - face.boundingBox.left.toFloat()
                else face.boundingBox.right.toFloat()

            canvas.drawRect(
                left * scaleWidth,
                face.boundingBox.top.toFloat() * scaleHeight,
                right * scaleWidth + 150,
                face.boundingBox.bottom.toFloat() * scaleHeight + 150,
                rectPaint
            )
        }
    }
}