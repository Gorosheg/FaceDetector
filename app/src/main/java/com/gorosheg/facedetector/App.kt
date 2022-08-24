package com.gorosheg.facedetector

import android.app.Application
import com.gorosheg.facedetector.di.faceDetectorModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(faceDetectorModule)
        }
    }
}