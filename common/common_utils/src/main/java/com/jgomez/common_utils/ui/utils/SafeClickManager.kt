package com.jgomez.common_utils.ui.utils

import android.os.SystemClock
import com.jgomez.common_utils.ui.utils.SafeClickManager.lastTimeClicked

object SafeClickManager {
    const val defaultButtonDebounceTimeMs = 450L
    var lastTimeClicked: Long = 0
}

inline fun debounce(internal : Long, crossinline  block: () -> Unit){
    if(SystemClock.elapsedRealtime() - lastTimeClicked > internal) {
        lastTimeClicked = SystemClock.elapsedRealtime()
        block()
    }
}

fun debounceOutline(internal : Long, block: () -> Unit){
    if(SystemClock.elapsedRealtime() - lastTimeClicked > internal) {
        lastTimeClicked = SystemClock.elapsedRealtime()
        block()
    }
}