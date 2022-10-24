package com.gorosheg.facedetector.presentation

import com.google.mlkit.vision.face.Face
import com.gorosheg.facedetector.presentation.camera.CameraPreview

data class FaceDetectorViewState(
    val cameraSelector: Int,
    val sourceInfo: CameraPreview.SourceInfo = CameraPreview.SourceInfo(10, 10, false),
    val detectedFaces: List<Face> = emptyList()
)