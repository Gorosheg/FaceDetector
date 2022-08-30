package com.gorosheg.facedetector.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.gorosheg.facedetector.presentation.FaceDetectorActivity.Companion.REQUEST_CODE_PERMISSIONS

fun enableCamera(activity: Activity): Boolean {
    return if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_GRANTED
    ) {
        true
    } else {
        requestPermissions(activity)
        false
    }
}

private fun requestPermissions(activity: Activity) {
    requestPermissions(
        activity,
        arrayOf(Manifest.permission.CAMERA),
        REQUEST_CODE_PERMISSIONS
    )
}