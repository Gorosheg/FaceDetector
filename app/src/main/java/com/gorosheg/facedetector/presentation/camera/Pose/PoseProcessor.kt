package com.gorosheg.facedetector.presentation.camera.Pose

import android.annotation.SuppressLint
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetector
import java.util.concurrent.Executor

class PoseProcessor(
    private val executor: Executor,
    private val detector: PoseDetector

) {
    fun stop() {
        detector.close()
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun processImageProxy(image: ImageProxy, onDetectionFinished: (Pose) -> Unit) {
        detector.process(InputImage.fromMediaImage(image.image!!, image.imageInfo.rotationDegrees))
            .addOnSuccessListener(executor) { results: Pose -> onDetectionFinished(results) }
    }
}
