package com.example.camerademo.utils

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureFailure
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.params.OutputConfiguration
import android.hardware.camera2.params.SessionConfiguration
import android.media.ImageReader
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Size
import android.view.Surface
import androidx.core.app.ActivityCompat
import androidx.core.util.Consumer
import com.example.camerademo.CameraApp.Companion.appContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object Camera2Helper {
    private val TAG = this.javaClass.simpleName
    private var mCameraId: String? = null

    private var mCurrentInternal: Int? = null
    private var mCameraCharacteristics: CameraCharacteristics? = null

    private var mRatio = Size(3, 4)

    private var mPreviewSize: Size? = null

    private var mCameraDevice: CameraDevice? = null

    private var mImageReader: ImageReader? = null

    private var mSession: CameraCaptureSession? = null

    private var mPreviewRequest: CaptureRequest? = null

    private var mPreviewRequestBuilder: CaptureRequest.Builder? = null
    private var mCaptureRequestBuilder: CaptureRequest.Builder? = null

    private var mCaptureRequest: CaptureRequest? = null

    private var mMediaRecorder: MediaRecorder? = null

    private var mRecordRequestBuilder: CaptureRequest.Builder? = null


    private val mHandler = Handler(Looper.getMainLooper())

    private lateinit var mCameraManager: CameraManager

    suspend fun preview(
        cameraManager: CameraManager, surfaceTexture: SurfaceTexture?, executor: Executor
    ) {
        mCameraManager = cameraManager
        chooseCameraId(cameraManager)
        mCameraDevice = openCamera(cameraManager)
        mImageReader = getImageReader()

        val surface = getSurface(surfaceTexture)

        mSession = startCaptureSession(
            surface, mImageReader!!.surface, executor, mCameraDevice!!
        )

        mPreviewRequestBuilder =
            mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        mCaptureRequestBuilder =
            mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)

        mPreviewRequestBuilder!!.addTarget(surface)

        mPreviewRequest = mPreviewRequestBuilder!!.build()
        mSession!!.setRepeatingRequest(mPreviewRequest!!, null, mHandler)
    }

    suspend fun takePhoto(rotation: Int, callback: Consumer<ByteArray>) {
        mCaptureRequestBuilder!!.addTarget(mImageReader!!.surface)
        mCaptureRequestBuilder!!.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

        mCaptureRequestBuilder!!.set(
            CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(mCameraCharacteristics!!, rotation)
        )
        mCaptureRequest = mCaptureRequestBuilder!!.build()

        mSession!!.capture(mCaptureRequest!!, object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureFailed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                failure: CaptureFailure
            ) {
                super.onCaptureFailed(session, request, failure)
                Log.e(TAG, "onCaptureFailed: fail=$failure, reason=${failure.reason}")
            }
        }, mHandler)

        mImageReader!!.setOnImageAvailableListener({
            val image = mImageReader!!.acquireNextImage()
            image?.use {
                val planes = it.planes
                if (planes.isNotEmpty()) {
                    val buffer = planes[0].buffer
                    val data = ByteArray(buffer.remaining())
                    buffer.get(data)

                    CoroutineScope(Dispatchers.IO).launch {
                        val imgPath = FileHelper.saveFile(
                            "IMG-${GenUtils.getTimeString()}.jpeg",
                            data
                        )
                        Log.i(TAG, "takePhoto: imgPath=$imgPath")
                    }

                    callback.accept(data)
                }
            }
        }, mHandler)

    }

    suspend fun startRecord(
        surfaceTexture: SurfaceTexture?, executor: Executor
    ) {
        setUpMediaRecorder()

        mRecordRequestBuilder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)

        val previewSurface = getSurface(surfaceTexture)
        mRecordRequestBuilder!!.addTarget(previewSurface)

        val recorderSurface = mMediaRecorder!!.surface
        mRecordRequestBuilder!!.addTarget(recorderSurface)

        mSession =
            startCaptureSession(previewSurface, recorderSurface, executor, mCameraDevice!!)

        mRecordRequestBuilder!!.set(
            CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO
        )
        mSession!!.setRepeatingRequest(mRecordRequestBuilder!!.build(), null, mHandler)

        mMediaRecorder!!.start()
    }

    suspend fun stopRecord(surfaceTexture: SurfaceTexture?, executor: Executor) =
        runCatching {
            mMediaRecorder?.let {
                it.stop()
                it.reset()
                it.release()
            }
            mMediaRecorder = null
            rePreview(surfaceTexture, executor)
        }.onFailure {
            it.printStackTrace()
        }

    suspend fun switchCamera(
        cameraManager: CameraManager,
        surfaceTexture: SurfaceTexture?,
        executor: Executor
    ) {
        for (cameraId in cameraManager.cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val level = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
            if (level != null && level != CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                val internal = characteristics.get(CameraCharacteristics.LENS_FACING)
                Log.i(TAG, "switchCamera: internal=$internal, current=$mCurrentInternal")
                if ((mCurrentInternal == CameraCharacteristics.LENS_FACING_BACK && internal == CameraCharacteristics.LENS_FACING_FRONT)
                    || (mCurrentInternal == CameraCharacteristics.LENS_FACING_FRONT && internal == CameraCharacteristics.LENS_FACING_BACK)
                ) {
                    mCurrentInternal = internal
                    mCameraId = cameraId
                    mCameraCharacteristics = characteristics
                    break
                }
            }
        }

        Log.i(TAG, "switchCamera: current=$mCurrentInternal, mCameraId=$mCameraId")

        rePreview(surfaceTexture, executor)
    }

    suspend fun updateRatio(ratio: Size, surfaceTexture: SurfaceTexture?, executor: Executor) {
        if (ratio == mRatio) return
        mRatio = ratio
        rePreview(surfaceTexture, executor)
    }

    fun zoomWhenPreview(zoom: Float) {
        val zoomRect = calculateZoomRect(zoom)

        mPreviewRequestBuilder!!.set(CaptureRequest.SCALER_CROP_REGION, zoomRect)
        mCaptureRequestBuilder!!.set(CaptureRequest.SCALER_CROP_REGION, zoomRect)
        mSession?.setRepeatingRequest(mPreviewRequestBuilder!!.build(), null, mHandler)
    }

    fun zoomWhenRecord(zoom: Float) {
        val zoomRect = calculateZoomRect(zoom)

        mSession?.stopRepeating()

        mRecordRequestBuilder!!.set(CaptureRequest.SCALER_CROP_REGION, zoomRect)
        mSession?.setRepeatingRequest(mRecordRequestBuilder!!.build(), null, mHandler)
    }

    fun release() {
        mCameraDevice?.close()
        mSession?.close()
        mMediaRecorder?.release()
        mSession?.close()
        mImageReader?.close()

        mCameraDevice = null
        mSession = null
        mImageReader = null
        mMediaRecorder = null
        mRecordRequestBuilder = null
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun chooseCameraId(cameraManager: CameraManager) {
        if (mCameraId == null) {
            for (cameraId in cameraManager.cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(cameraId)
                val level = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
                if (level != null && level != CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                    val internal = characteristics.get(CameraCharacteristics.LENS_FACING)
                    Log.i(TAG, "chooseCameraId: internal=$internal, current=$mCurrentInternal")
                    if (internal == CameraCharacteristics.LENS_FACING_BACK) {
                        mCameraId = cameraId
                        mCurrentInternal = internal
                        mCameraCharacteristics = characteristics
                        break
                    }
                }
            }
        }

        mCameraCharacteristics?.let {
            val map = it.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

            val largest = map?.getOutputSizes(ImageFormat.JPEG).getOptimalPreviewSize(mRatio)

            mPreviewSize = largest
        }
    }


    private suspend fun openCamera(cameraManager: CameraManager) = suspendCoroutine {
        Log.i(TAG, "openCamera: mCameraId=$mCameraId")
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "openCamera: CAMERA permission is not Granted")
            return@suspendCoroutine
        }
        cameraManager.openCamera(mCameraId!!, object : CameraDevice.StateCallback() {
            override fun onDisconnected(camera: CameraDevice) {
                Log.e(TAG, "onDisconnected: ")
                camera.close()
            }

            override fun onError(camera: CameraDevice, error: Int) {
                Log.e(TAG, "onError: $error")
                camera.close()
            }

            override fun onOpened(camera: CameraDevice) {
                Log.i(TAG, "onOpened: ")
                it.resume(camera)
            }

        }, mHandler)
    }

    private fun getImageReader(): ImageReader {
        return ImageReader.newInstance(
            mPreviewSize!!.width, mPreviewSize!!.height, ImageFormat.JPEG, 2
        )
    }

    private fun getSurface(surfaceTexture: SurfaceTexture?): Surface {
        val width = mPreviewSize!!.width
        val height = mPreviewSize!!.height

        surfaceTexture?.setDefaultBufferSize(width, height)
        return Surface(surfaceTexture)
    }

    private suspend fun startCaptureSession(
        previewSurface: Surface,
        imageReaderSurface: Surface,
        executor: Executor,
        cameraDevice: CameraDevice
    ) = suspendCoroutine {
        val outputConfigurations = arrayListOf<OutputConfiguration>()
        outputConfigurations.add(OutputConfiguration(previewSurface))
        outputConfigurations.add(OutputConfiguration(imageReaderSurface))

        val stateCallback = object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                it.resume(session)
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {
                Log.e(TAG, "Session configuration failed")
                it.resumeWithException(Exception("onConfigureFailed"))
            }
        }

        val sessionConfig = SessionConfiguration(
            SessionConfiguration.SESSION_REGULAR, outputConfigurations, executor, stateCallback
        )
        cameraDevice.createCaptureSession(sessionConfig)
    }

    private fun getJpegOrientation(
        cameraCharacteristics: CameraCharacteristics, deviceOrientation: Int
    ): Int {
        val sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)

        val deviceRotation = when (deviceOrientation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }

        return (sensorOrientation!! + deviceRotation + 360) % 360
    }

    private fun setUpMediaRecorder() {
        if (mMediaRecorder == null) {
            mMediaRecorder = MediaRecorder(appContext)
        }

        mMediaRecorder?.run {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)

            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

            val fileDescriptor =
                FileHelper.createVideoFileDescriptor("VID-${GenUtils.getTimeString()}.mp4")
            setOutputFile(fileDescriptor)

            setVideoEncodingBitRate(100000000)
            setVideoFrameRate(30)

            setVideoSize(mPreviewSize!!.width, mPreviewSize!!.height)

            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)

            setOrientationHint(90)

            prepare()
        }
        Log.i(TAG, "setUpMediaRecorder: mMediaRecorder=$mMediaRecorder")
    }

    private fun calculateZoomRect(zoomLevel: Float): Rect {
        val sensorRect = mCameraCharacteristics!!.get(
            CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE
        )!!

        val minZoom = 1.0f
        val maxZoom = mCameraCharacteristics!!.get(
            CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM
        )!!
        val currentZoom = minZoom + (maxZoom - minZoom) * zoomLevel

        val centerX = sensorRect.width() / 2
        val centerY = sensorRect.height() / 2
        val deltaX = (0.5f * sensorRect.width() / currentZoom).toInt()
        val deltaY = (0.5f * sensorRect.height() / currentZoom).toInt()

        val zoomRect = Rect()
        zoomRect.left = centerX - deltaX
        zoomRect.right = centerX + deltaX
        zoomRect.top = centerY - deltaY
        zoomRect.bottom = centerY + deltaY

        return zoomRect
    }

    private suspend fun rePreview(surfaceTexture: SurfaceTexture?, executor: Executor) {
        release()
        preview(mCameraManager, surfaceTexture, executor)
    }
}