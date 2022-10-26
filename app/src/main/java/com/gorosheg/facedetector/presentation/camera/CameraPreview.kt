package com.gorosheg.facedetector.presentation.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.TaskExecutors
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.pose.Pose
import com.gorosheg.facedetector.model.ImageSourceInfo
import com.gorosheg.facedetector.presentation.FaceDetectorActivity
import com.gorosheg.facedetector.presentation.camera.Face.FaceProcessor
import com.gorosheg.facedetector.presentation.camera.Pose.PoseProcessor

class CameraPreview(
    private val faceProcessor: FaceProcessor,
    private val poseProcessor: PoseProcessor
) {
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

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(activity, cameraSelector, preview, analysis)
        }, ContextCompat.getMainExecutor(activity))
    }

    private fun buildImageAnalysis(
        lens: Int,
        setSourceInfo: (ImageSourceInfo) -> Unit,
        onFacesDetected: (List<Face>) -> Unit,
        onPoseDetected: (Pose) -> Unit
    ): ImageAnalysis {
        var sourceInfoUpdated = false

        return ImageAnalysis.Builder().build().apply {
            setAnalyzer(
                TaskExecutors.MAIN_THREAD
            ) { imageProxy: ImageProxy ->
                if (!sourceInfoUpdated) {
                    setSourceInfo(getSourceInfo(lens, imageProxy)) // получаем размеры картинки
                    sourceInfoUpdated = true
                }

                faceProcessor.processImageProxy(imageProxy, onFacesDetected)
                poseProcessor.processImageProxy(imageProxy, onPoseDetected)
            }
        }
    }

    private fun getSourceInfo(lens: Int, imageProxy: ImageProxy): ImageSourceInfo {
        val isImageFlipped = lens == CameraSelector.LENS_FACING_FRONT
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        return if (rotationDegrees == 0 || rotationDegrees == ROTATIONDEGREES) {
            ImageSourceInfo(
                height = imageProxy.height,
                width = imageProxy.width,
                isImageFlipped = isImageFlipped
            )
        } else {
            ImageSourceInfo(
                height = imageProxy.width,
                width = imageProxy.height,
                isImageFlipped = isImageFlipped
            )
        }
    }

    companion object {
        private const val ROTATIONDEGREES = 180
    }
}