package com.gorosheg.facedetector.presentation.camera

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View
import androidx.camera.view.PreviewView
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

class PoseDraw(
    context: Context,
    private val pose: Pose?,
    private val sourceInfo: CameraPreview.SourceInfo,
    previewView: PreviewView
) : View(context) {

    private val needToMirror = sourceInfo.isImageFlipped

    private val linePaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8F
    }

    /* private val scaleHeight = (previewView.height / sourceInfo.height).toFloat()
     private val scaleWidth = (previewView.width / sourceInfo.width).toFloat()*/

    private val scale = (previewView.height * previewView.width) / (sourceInfo.width * sourceInfo.height) / 2

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        if (pose != null) {
            val whitePaint = Color.WHITE
            val leftPaint = Color.GREEN
            val rightPaint = Color.YELLOW

            val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER) // плечо
            val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
            val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
            val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW) // локоть
            val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST) // запястье
            val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
            val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP) // бедро
            val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
            val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE) // колено
            val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
            val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE) // лодыжка
            val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

            val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY) // мезинец
            val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
            val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX) // указательный палец
            val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
            val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB) // большой палец
            val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
            val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL) // пятка
            val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
            val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX) // указательный палец ноги
            val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

            canvas.drawLine(leftShoulder, rightShoulder, whitePaint)
            canvas.drawLine(leftHip, rightHip, whitePaint)
            // Left body
            canvas.drawLine(leftShoulder, leftElbow, leftPaint)
            canvas.drawLine(leftElbow, leftWrist, leftPaint)
            canvas.drawLine(leftShoulder, leftHip, leftPaint)
            canvas.drawLine(leftHip, leftKnee, leftPaint)
            canvas.drawLine(leftKnee, leftAnkle, leftPaint)
            canvas.drawLine(leftWrist, leftThumb, leftPaint)
            canvas.drawLine(leftWrist, leftPinky, leftPaint)
            canvas.drawLine(leftWrist, leftIndex, leftPaint)
            canvas.drawLine(leftIndex, leftPinky, leftPaint)
            canvas.drawLine(leftAnkle, leftHeel, leftPaint)
            canvas.drawLine(leftHeel, leftFootIndex, leftPaint)
            // Right body
            canvas.drawLine(rightShoulder, rightElbow, rightPaint)
            canvas.drawLine(rightElbow, rightWrist, rightPaint)
            canvas.drawLine(rightShoulder, rightHip, rightPaint)
            canvas.drawLine(rightHip, rightKnee, rightPaint)
            canvas.drawLine(rightKnee, rightAnkle, rightPaint)
            canvas.drawLine(rightWrist, rightThumb, rightPaint)
            canvas.drawLine(rightWrist, rightPinky, rightPaint)
            canvas.drawLine(rightWrist, rightIndex, rightPaint)
            canvas.drawLine(rightIndex, rightPinky, rightPaint)
            canvas.drawLine(rightAnkle, rightHeel, rightPaint)
            canvas.drawLine(rightHeel, rightFootIndex, rightPaint)
        }
    }

    private fun Canvas.drawLine(
        startPoint: PoseLandmark?,
        endPoint: PoseLandmark?,
        drawingColor: Int
    ) {
        if (startPoint != null && endPoint != null) {
            val startX =
                if (needToMirror) sourceInfo.width - startPoint.position.x
                else startPoint.position.x
            val startY = startPoint.position.y

            val endX =
                if (needToMirror) sourceInfo.width - endPoint.position.x
                else endPoint.position.x
            val endY = endPoint.position.y

            this.drawLine(
                startX * scale-100,
                startY * scale-100,
                endX * scale-100,
                endY * scale-100,
                linePaint.apply {
                    color = drawingColor
                }
            )
        }
    }
}