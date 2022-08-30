package com.gorosheg.facedetector.presentation

import androidx.camera.view.PreviewView
import androidx.lifecycle.ViewModel

class FaceDetectorViewModel(private val cameraPreview: CameraPreview) : ViewModel() {

    fun startCamera(activity: FaceDetectorActivity, previewView: PreviewView) {
        cameraPreview.startCamera(activity, previewView)
    }

    fun takePhoto() {}

}