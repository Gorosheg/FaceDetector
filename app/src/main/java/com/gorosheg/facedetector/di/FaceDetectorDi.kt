package com.gorosheg.facedetector.di

import com.gorosheg.facedetector.domain.FaceDetectorInteractor
import com.gorosheg.facedetector.domain.FaceDetectorInteractorImpl
import com.gorosheg.facedetector.presentation.FaceDetectorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val faceDetectorModule = module {
    viewModel {
        FaceDetectorViewModel(get())
    }

    factory<FaceDetectorInteractor> {
        FaceDetectorInteractorImpl()
    }

}