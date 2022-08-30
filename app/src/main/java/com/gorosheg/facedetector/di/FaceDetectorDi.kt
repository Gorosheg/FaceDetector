package com.gorosheg.facedetector.di

import com.gorosheg.facedetector.presentation.CameraPreview
import com.gorosheg.facedetector.presentation.FaceDetectorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val faceDetectorModule = module {
    viewModel { params ->
        FaceDetectorViewModel(cameraPreview = get { params })
    }

    factory { params ->
        CameraPreview((params.get()))
    }
}