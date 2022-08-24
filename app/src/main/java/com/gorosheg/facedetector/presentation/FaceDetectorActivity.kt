package com.gorosheg.facedetector.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gorosheg.facedetector.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaceDetectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: FaceDetectorViewModel by viewModel()
    }
}