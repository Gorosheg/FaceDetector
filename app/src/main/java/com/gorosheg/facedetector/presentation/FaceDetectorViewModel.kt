package com.gorosheg.facedetector.presentation

import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import com.gorosheg.facedetector.core.BaseViewModel
import com.gorosheg.facedetector.extentions.requireValue
import com.gorosheg.facedetector.extentions.set
import com.gorosheg.facedetector.extentions.update
import com.gorosheg.facedetector.model.CameraLens
import com.gorosheg.facedetector.presentation.camera.CameraPreview

class FaceDetectorViewModel(private val cameraPreview: CameraPreview) : BaseViewModel<FaceDetectorViewState>() {

    init {
        viewState.set(
            FaceDetectorViewState(
                cameraLens = CameraLens(CameraSelector.LENS_FACING_BACK, false)
            )
        )
    }

    fun startCamera(activity: FaceDetectorActivity, previewView: PreviewView) {
        cameraPreview.startCamera(
            activity,
            previewView,
            viewState.requireValue().cameraLens.lens,
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

        if (viewState.requireValue().cameraLens.isChanging) {
            updateCameraLens(viewState.requireValue().cameraLens.lens, false)
        }
    }

    fun switchCamera() {
        if (viewState.requireValue().cameraLens.lens == CameraSelector.LENS_FACING_BACK) {
            updateCameraLens(CameraSelector.LENS_FACING_FRONT, true)
        } else {
            updateCameraLens(CameraSelector.LENS_FACING_BACK, true)
        }
    }

    private fun updateCameraLens(newSelector: Int, isChanging: Boolean) {
        viewState.update {
            copy(cameraLens = CameraLens(newSelector, isChanging))
        }
    }

    fun takePhoto() {}
}