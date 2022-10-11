package com.gorosheg.facedetector.extentions

import androidx.lifecycle.MutableLiveData

inline fun <reified T : Any> MutableLiveData<T>.set(newState: T) {
    value = newState
}

fun <T> MutableLiveData<T>.requireValue(messageIfNull: String = "required value was null and not set"): T {
    return this.value ?: error(messageIfNull)
}

inline fun <reified T : Any> MutableLiveData<T>.update(action: T.() -> T) {
    value = action.invoke(requireValue())
}