package com.gorosheg.facedetector.presentation.camera.Face

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import java.util.concurrent.Executor

class FaceProcessor(
    private val executor: Executor,
    private val detector: FaceDetector
) {
    fun stop() {
        detector.close()
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun processImageProxy(image: ImageProxy, onDetectionFinished: (List<Face>) -> Unit) {
        detector.process(InputImage.fromMediaImage(image.image!!, image.imageInfo.rotationDegrees))
            .addOnSuccessListener(executor) { results: List<Face> -> onDetectionFinished(results) }
            .addOnFailureListener(executor) { e: Exception ->
                Log.e("Camera", "Error detecting face", e)
            }
            .addOnCompleteListener { image.close() }
    }
}