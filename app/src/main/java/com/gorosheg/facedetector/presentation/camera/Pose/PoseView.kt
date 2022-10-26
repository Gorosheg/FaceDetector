package com.gorosheg.facedetector.presentation.camera.pose//

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View
import androidx.camera.view.PreviewView
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import com.gorosheg.facedetector.model.ImageSourceInfo

class PoseView(
    context: Context,
    private val pose: Pose?,
    private val imageSourceInfo: ImageSourceInfo,
    previewView: PreviewView
) : View(context) {
    private val whitePaint = Color.WHITE
    private val leftPaint = Color.GREEN
    private val rightPaint = Color.YELLOW
    private val needToMirror = imageSourceInfo.isImageFlipped

    private val linePaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = WIDTH
    }

    private val scale = (previewView.height * previewView.width) / (imageSourceInfo.width * imageSourceInfo.height) / 2

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawBody(canvas)
    }

    private fun drawBody(canvas: Canvas) {
        if (pose == null) return
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER) // плечо
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP) // бедро
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)

        canvas.drawLine(leftShoulder, rightShoulder, whitePaint)
        canvas.drawLine(leftHip, rightHip, whitePaint)

        drawLeftBodyPart(canvas)
        drawRightBodyPart(canvas)
    }

    private fun drawLeftBodyPart(canvas: Canvas) {
        if (pose == null) return
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST) // запястье
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE) // колено
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE) // лодыжка
        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY) // мезинец
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX) // указательный палец
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB) // большой палец
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL) // пятка
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX) // указательный палец ноги
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER) // плечо
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP) // бедро

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
    }

    private fun drawRightBodyPart(canvas: Canvas) {
        if (pose == null) return
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW) // локоть
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)

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

    private fun Canvas.drawLine(
        startPoint: PoseLandmark?,
        endPoint: PoseLandmark?,
        drawingColor: Int
    ) {
        if (startPoint == null || endPoint == null) return

        val startX =
            if (needToMirror) imageSourceInfo.width - startPoint.position.x
            else startPoint.position.x

        val startY = startPoint.position.y

        val endX =
            if (needToMirror) imageSourceInfo.width - endPoint.position.x
            else endPoint.position.x

        val endY = endPoint.position.y

        drawLine(
            startX * scale - LACK,
            startY * scale - LACK,
            endX * scale - LACK,
            endY * scale - LACK,
            linePaint.apply {
                color = drawingColor
            }
        )
    }

    companion object {
        private const val WIDTH = 8F
        private const val LACK = 100
    }
}