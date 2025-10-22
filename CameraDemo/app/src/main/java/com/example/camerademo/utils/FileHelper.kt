package com.example.camerademo.utils

import android.content.ContentValues
import android.provider.MediaStore
import com.example.camerademo.CameraApp.Companion.appContext
import java.io.FileDescriptor
import java.io.IOException

object FileHelper {
    private val TAG = this.javaClass.simpleName
    fun saveFile(filename: String, bytes: ByteArray?): String? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/demo")
        }

        val contentResolver = appContext.contentResolver
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            try {
                contentResolver.openOutputStream(uri).use { outputStream ->
                    outputStream?.write(bytes)
                    outputStream?.flush()
                }
                return uri.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun createVideoFileDescriptor(filename: String): FileDescriptor? {
        val values = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, filename)
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/demo")
        }

        val resolver = appContext.contentResolver
        val uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            val pfd = resolver.openFileDescriptor(uri, "rw")
            return pfd?.fileDescriptor
        }
        return null
    }
}