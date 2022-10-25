package com.gorosheg.facedetector.presentation

import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.pose.Pose
import com.gorosheg.facedetector.model.CameraLens
import com.gorosheg.facedetector.model.ImageSourceInfo

data class FaceDetectorViewState(
    val cameraLens: CameraLens,
    val imageSourceInfo: ImageSourceInfo = ImageSourceInfo(10, 10, false),
    val detectedFaces: List<Face> = emptyList(),
    val detectedPose: Pose? = null
)