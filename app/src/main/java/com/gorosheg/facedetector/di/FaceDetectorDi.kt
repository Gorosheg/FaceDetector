package com.gorosheg.facedetector.di

import com.gorosheg.facedetector.presentation.camera.CameraPreview
import com.gorosheg.facedetector.presentation.FaceDetectorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val faceDetectorModule = module {
    viewModel {
        FaceDetectorViewModel(cameraPreview = get())
    }

    factory {
        CameraPreview()
    }
}