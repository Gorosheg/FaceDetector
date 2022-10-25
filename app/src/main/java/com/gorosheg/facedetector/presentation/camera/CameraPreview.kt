package com.gorosheg.facedetector.presentation.camera

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.TaskExecutors
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.pose.Pose
import com.gorosheg.facedetector.model.ImageSourceInfo
import com.gorosheg.facedetector.presentation.FaceDetectorActivity
import com.gorosheg.facedetector.presentation.camera.Face.FaceDetector

class CameraPreview {
    fun startCamera(
        activity: FaceDetectorActivity,
        previewView: PreviewView,
        cameraLens: Int,
        setSourceInfo: (ImageSourceInfo) -> Unit,
        onFacesDetected: (List<Face>) -> Unit,
        onPoseDetected: (Pose) -> Unit
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener({
            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

            val cameraSelector: CameraSelector = CameraSelector.Builder().requireLensFacing(cameraLens).build()
            val analysis = buildImageAnalysis(cameraLens, setSourceInfo, onFacesDetected, onPoseDetected)
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(activity, cameraSelector, preview, analysis)

            } catch (e: Exception) {
                Log.e(TAG, "camera binding failed", e)
            }
        }, ContextCompat.getMainExecutor(activity))
    }

    private fun buildImageAnalysis(
        lens: Int,
        setSourceInfo: (ImageSourceInfo) -> Unit,
        onFacesDetected: (List<Face>) -> Unit,
        onPoseDetected: (Pose) -> Unit
    ): ImageAnalysis? {

        val faceProcessor = try {
            FaceDetector()
        } catch (e: Exception) {
            Log.e("CAMERA", "Can not create Face detector", e)
            return null
        }

        val poseProcessor = try {
            com.gorosheg.facedetector.presentation.camera.Pose.PoseDetector()
        } catch (e: Exception) {
            Log.e("CAMERA", "Can not create Pose detector", e)
            return null
        }

        var sourceInfoUpdated = false
        val imageAnalysis = ImageAnalysis.Builder().build()

        imageAnalysis.setAnalyzer(
            TaskExecutors.MAIN_THREAD
        ) { imageProxy: ImageProxy ->
            if (!sourceInfoUpdated) {
                setSourceInfo(getSourceInfo(lens, imageProxy)) // получаем размеры картинки
                sourceInfoUpdated = true
            }
            try {
                faceProcessor.processImageProxy(imageProxy, onFacesDetected) // когда лицо определиться, вызвать колбэк
                poseProcessor.processImageProxy(imageProxy, onPoseDetected)
            } catch (e: MlKitException) {
                Log.e(
                    "CAMERA", "Failed to process image. Error: " + e.localizedMessage
                )
            }
        }
        return imageAnalysis
    }

    private fun getSourceInfo(lens: Int, imageProxy: ImageProxy): ImageSourceInfo {
        val isImageFlipped = lens == CameraSelector.LENS_FACING_FRONT
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        return if (rotationDegrees == 0 || rotationDegrees == 180) {
            ImageSourceInfo(
                height = imageProxy.height, width = imageProxy.width, isImageFlipped = isImageFlipped
            )
        } else {
            ImageSourceInfo(
                height = imageProxy.width, width = imageProxy.height, isImageFlipped = isImageFlipped
            )
        }
    }

    companion object {
        private const val TAG = "BindingCamera"
    }
}