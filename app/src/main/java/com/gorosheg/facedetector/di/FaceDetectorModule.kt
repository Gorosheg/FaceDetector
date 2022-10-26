package com.gorosheg.facedetector.di

import com.google.android.gms.tasks.TaskExecutors
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.gorosheg.facedetector.presentation.FaceDetectorViewModel
import com.gorosheg.facedetector.presentation.camera.CameraPreview
import com.gorosheg.facedetector.presentation.camera.Face.FaceProcessor
import com.gorosheg.facedetector.presentation.camera.Pose.PoseProcessor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val faceDetectorModule = module {
    viewModel {
        FaceDetectorViewModel(cameraPreview = get())
    }

    factory {
        CameraPreview(get(), get())
    }

    factory {
        PoseProcessor(
            executor = get(),
            detector = get()
        )
    }

    factory {
        FaceProcessor(
            executor = get(),
            detector = get()
        )
    }

    factory {
        TaskExecutors.MAIN_THREAD
    }

    factory<PoseDetector> {
        PoseDetection.getClient(get<PoseDetectorOptionsBase>())
    }

    factory<PoseDetectorOptionsBase> {
        PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
    }

    factory {
        FaceDetection.getClient(get())
    }

    factory {
        FaceDetectorOptions.Builder()
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.4f)
            .build()
    }
}