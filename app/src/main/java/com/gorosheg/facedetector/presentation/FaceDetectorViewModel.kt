package com.gorosheg.facedetector.presentation

import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import com.gorosheg.facedetector.core.BaseViewModel
import com.gorosheg.facedetector.extentions.requireValue
import com.gorosheg.facedetector.extentions.set
import com.gorosheg.facedetector.extentions.update
import com.gorosheg.facedetector.presentation.camera.CameraPreview

class FaceDetectorViewModel(private val cameraPreview: CameraPreview) : BaseViewModel<FaceDetectorViewState>() {

    init {
        viewState.set(
            FaceDetectorViewState(cameraSelector = CameraSelector.LENS_FACING_BACK)
        )
    }

    fun startCamera(activity: FaceDetectorActivity, previewView: PreviewView) {
        cameraPreview.startCamera(
            activity,
            previewView,
            viewState.requireValue().cameraSelector,
            setSourceInfo = {
                viewState.update {
                    copy(sourceInfo = it)
                }
            },
            onFacesDetected = {
                viewState.update {
                    copy(detectedFaces = it)
                }
            }
        )
    }

    fun takePhoto() {}

    fun switchCamera() {
        if (viewState.requireValue().cameraSelector == CameraSelector.LENS_FACING_BACK) {
            updateViewState(CameraSelector.LENS_FACING_FRONT)
        } else {
            updateViewState(CameraSelector.LENS_FACING_BACK)
        }
    }

    private fun updateViewState(newSelector: Int) {
        viewState.update {
            copy(cameraSelector = newSelector)
        }
    }
}