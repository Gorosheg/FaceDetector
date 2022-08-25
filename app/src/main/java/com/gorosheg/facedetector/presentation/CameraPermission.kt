package com.gorosheg.facedetector.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.gorosheg.facedetector.presentation.FaceDetectorActivity.Companion.REQUEST_CODE_PERMISSIONS
import kotlin.reflect.KFunction1

fun enableCamera(activity: Activity, startCamera: () -> Unit) {
    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        startCamera.invoke()
    } else {
        requestPermissions(activity)
    }
}

private fun requestPermissions(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.CAMERA),
        REQUEST_CODE_PERMISSIONS
    )
}