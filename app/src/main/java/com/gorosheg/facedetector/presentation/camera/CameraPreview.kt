package com.gorosheg.facedetector.presentation.camera

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.gorosheg.facedetector.presentation.FaceDetectorActivity

class CameraPreview {
    fun startCamera(
        activity: FaceDetectorActivity,
        previewView: PreviewView,
        cameraLens: Int,
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener({
            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

            val cameraSelector: CameraSelector = when (cameraLens) {
                0 -> CameraSelector.DEFAULT_FRONT_CAMERA
                else -> CameraSelector.DEFAULT_BACK_CAMERA
            }

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(activity, cameraSelector, preview /*imageCapture*/)

            } catch (exc: Exception) {
                Log.e(TAG, "camera binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(activity))
    }

    companion object {
        private const val TAG = "BindingCamera"
    }
}