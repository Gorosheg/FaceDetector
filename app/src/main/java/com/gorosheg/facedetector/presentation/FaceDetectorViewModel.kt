package com.gorosheg.facedetector.presentation

import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import com.gorosheg.facedetector.core.BaseViewModel
import com.gorosheg.facedetector.extentions.requireValue
import com.gorosheg.facedetector.extentions.update
import com.gorosheg.facedetector.model.Camera
import com.gorosheg.facedetector.presentation.camera.CameraPreview

class FaceDetectorViewModel(private val cameraPreview: CameraPreview) : BaseViewModel<FaceDetectorViewState>() {

    init {
        viewState.value = FaceDetectorViewState(
            camera = Camera(CameraSelector.LENS_FACING_BACK, false)
        )
    }

    fun startCamera(activity: FaceDetectorActivity, previewView: PreviewView) {
        cameraPreview.startCamera(
            activity = activity,
            previewView = previewView,
            cameraLens = viewState.requireValue().camera.lens,
            setSourceInfo = {
                viewState.update {
                    copy(imageSourceInfo = it)
                }
            },
            onFacesDetected = {
                viewState.update {
                    copy(detectedFaces = it)
                }
            },
            onPoseDetected = {
                viewState.update {
                    copy(detectedPose = it)
                }
            }
        )

        if (viewState.requireValue().camera.isChanging) {
            updateCameraLens(viewState.requireValue().camera.lens, false)
        }
    }

    fun switchCamera() {
        if (viewState.requireValue().camera.lens == CameraSelector.LENS_FACING_BACK) {
            updateCameraLens(CameraSelector.LENS_FACING_FRONT, true)
        } else {
            updateCameraLens(CameraSelector.LENS_FACING_BACK, true)
        }
    }

    private fun updateCameraLens(newSelector: Int, isChanging: Boolean) {
        viewState.update {
            copy(camera = Camera(newSelector, isChanging))
        }
    }
    fun takePhoto(){}
}