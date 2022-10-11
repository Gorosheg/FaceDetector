package com.gorosheg.facedetector.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.lifecycle.Observer
import com.gorosheg.facedetector.R
import com.gorosheg.facedetector.presentation.camera.cameraPermissionGranted
import com.gorosheg.facedetector.presentation.camera.requestPermissions
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaceDetectorActivity : AppCompatActivity() {
    private val previewView by lazy { findViewById<PreviewView>(R.id.previewView) }

    private val viewModel: FaceDetectorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (cameraPermissionGranted(this)) {
            viewModel.startCamera(this, previewView)
        } else {
            requestPermissions(this)
        }

        val takePhotoButton: Button = findViewById(R.id.take_photo_button)
        val switchCameraButton: Button = findViewById(R.id.switchCameraButton)
        switchCameraButton.setOnClickListener { viewModel.switchCamera() }
        takePhotoButton.setOnClickListener { viewModel.takePhoto() }

        viewModel.viewState.observe(this, Observer(this::onStateChanged))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.startCamera(this, previewView)
            } else {
                Toast.makeText(this, "Can't open camera", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onStateChanged(viewState: FaceDetectorViewState) {
        viewModel.startCamera(this, previewView)
    }
}