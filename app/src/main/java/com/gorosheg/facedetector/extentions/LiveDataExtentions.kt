package com.gorosheg.facedetector.extentions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.requireValue(): T {
    return value ?: error("required value was null and not set")
}

inline fun <reified T : Any> MutableLiveData<T>.update(action: T.() -> T) {
    value = action.invoke(requireValue())
}