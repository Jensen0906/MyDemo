package com.example.camerademo.utils

import android.util.Size
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs

object GenUtils {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd_HHmmss", Locale.CHINESE)
    fun getTimeString(): String = simpleDateFormat.format(System.currentTimeMillis())

}

fun Array<Size>?.getOptimalPreviewSize(w: Int, h: Int): Size {
    if (this.isNullOrEmpty()) return Size(1080, 1920)
    val targetRatio = w.toDouble() / h
    val size = this.filter { it.width < 2000 }
        .minByOrNull { abs(it.width.toDouble() / it.height - targetRatio) }
    return size ?: this[0]
}

fun Array<Size>?.getOptimalPreviewSize(ratio: Size): Size =
    this.getOptimalPreviewSize(ratio.height, ratio.width)