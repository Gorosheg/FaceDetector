package com.gorosheg.facedetector.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import com.gorosheg.facedetector.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FaceDetectorActivity : AppCompatActivity() {
    private val previewView by lazy { findViewById<PreviewView>(R.id.previewView) }

    private val viewModel: FaceDetectorViewModel by viewModel {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (enableCamera(this)) {
            viewModel.startCamera(this, previewView)
        }

        val takePhotoButton: Button = findViewById(R.id.take_photo_button)
        takePhotoButton.setOnClickListener { viewModel.takePhoto() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.startCamera(this, previewView)
            } else {
                Toast.makeText(this, "Can't open camera", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 0
    }
}