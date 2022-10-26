package com.gorosheg.facedetector.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.lifecycle.Observer
import com.gorosheg.facedetector.R
import com.gorosheg.facedetector.presentation.camera.face.FaceView
import com.gorosheg.facedetector.presentation.camera.cameraPermissionGranted
import com.gorosheg.facedetector.presentation.camera.requestPermissions
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaceDetectorActivity : AppCompatActivity(R.layout.activity_main) {
    private val previewView by lazy { findViewById<PreviewView>(R.id.previewView) }
    private val viewModel: FaceDetectorViewModel by viewModel()
    private val faceView by lazy { FaceView(this, previewView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (cameraPermissionGranted(this)) {
            startCamera()
        } else {
            requestPermissions(this)
        }

        viewModel.viewState.observe(this, Observer(this::onStateChanged))

        val takePhotoButton: Button = findViewById(R.id.takePhotoButton)
        val switchCameraButton: Button = findViewById(R.id.switchCameraButton)
        takePhotoButton.setOnClickListener { viewModel.takePhoto() }
        switchCameraButton.setOnClickListener { viewModel.switchCamera() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(this, "Can't open camera", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onStateChanged(viewState: FaceDetectorViewState) {
        if (viewState.camera.isChanging) {
            startCamera()
        }
        faceView.invalidateFaceView(viewState.detectedFaces, viewState.imageSourceInfo)
        previewView.removeView(faceView)
        previewView.addView(faceView)
        /*val poseShape = PoseDraw(this, viewState.detectedPose, viewState.sourceInfo, previewView)
        previewView.addView(poseShape)*/
    }

    private fun startCamera() {
        viewModel.startCamera(this, previewView)
    }
}