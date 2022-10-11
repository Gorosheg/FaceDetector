package com.gorosheg.facedetector.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T> : ViewModel() {
    val viewState = MutableLiveData<T>()
}