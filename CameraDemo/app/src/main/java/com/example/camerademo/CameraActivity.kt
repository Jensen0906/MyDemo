package com.example.camerademo

import android.Manifest
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.camerademo.databinding.ActivityCameraBinding
import com.example.camerademo.utils.Camera2Helper
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class  CameraActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityCameraBinding
    private var isRecording = false

    private var ratio = "3:4"

    private var zoom = 0.0f

    private var available = false

    private var allGranted = false

    private val mTextureViewCallback = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(
            surface: SurfaceTexture, width: Int, height: Int
        ) {
            Log.i(TAG, "onSurfaceTextureAvailable: ")
            available = true
            if (allGranted) startPreview()
        }

        override fun onSurfaceTextureSizeChanged(
            surface: SurfaceTexture, width: Int, height: Int
        ) {
            Log.i(TAG, "onSurfaceTextureSizeChanged: ")
            binding.recoderBtn.isEnabled = ratio == "9:16"
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            Log.e(TAG, "onSurfaceTextureDestroyed: ")
            available = false
            Camera2Helper.release()
            return true
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.preview.surfaceTextureListener = mTextureViewCallback

        binding.takePhotoBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Camera2Helper.takePhoto(display.rotation) {
                    Glide.with(this@CameraActivity).load(it)
                        .apply(
                            RequestOptions().transform(
                                MultiTransformation(CenterCrop(), RoundedCorners(16))
                            )
                        ).into(binding.photoIv)
                }
            }
        }

        binding.recoderBtn.setOnClickListener {
            isRecording = !isRecording
            binding.takePhotoBtn.isEnabled = !isRecording
            binding.switchBtn.isEnabled = !isRecording
            binding.recoderBtn.text = if (isRecording) "停止" else "录像"

            lifecycleScope.launch {
                if (isRecording) Camera2Helper.startRecord(
                    binding.preview.surfaceTexture,
                    ContextCompat.getMainExecutor(this@CameraActivity)
                )
                else Camera2Helper.stopRecord(
                    binding.preview.surfaceTexture,
                    ContextCompat.getMainExecutor(this@CameraActivity)
                )
            }
        }

        binding.switchBtn.setOnClickListener {
            lifecycleScope.launch {
                val cameraManager =
                    ContextCompat.getSystemService(this@CameraActivity, CameraManager::class.java)
                if (cameraManager == null) {
                    Log.e(TAG, "onResume: get CameraManager is null")
                    return@launch
                }
                Camera2Helper.switchCamera(
                    cameraManager,
                    binding.preview.surfaceTexture,
                    ContextCompat.getMainExecutor(this@CameraActivity)
                )
            }
        }

        binding.ratioBtn.setOnClickListener {
            lifecycleScope.launch {
                when (ratio) {
                    "3:4" -> {
                        updateRatio("9:16")
                        Camera2Helper.updateRatio(
                            Size(9, 16),
                            binding.preview.surfaceTexture,
                            ContextCompat.getMainExecutor(this@CameraActivity)
                        )
                    }

                    "9:16" -> {
                        updateRatio("1:1")
                        Camera2Helper.updateRatio(
                            Size(1, 1),
                            binding.preview.surfaceTexture,
                            ContextCompat.getMainExecutor(this@CameraActivity)
                        )
                    }

                    "1:1" -> {
                        updateRatio("3:4")
                        Camera2Helper.updateRatio(
                            Size(3, 4),
                            binding.preview.surfaceTexture,
                            ContextCompat.getMainExecutor(this@CameraActivity)
                        )
                    }

                    else -> {
                        updateRatio("3:4")
                        Camera2Helper.updateRatio(
                            Size(3, 4),
                            binding.preview.surfaceTexture,
                            ContextCompat.getMainExecutor(this@CameraActivity)
                        )
                    }
                }
            }

        }

        binding.scaleBtn.setOnClickListener {
            zoom += 0.05f
            if (zoom > 0.3f) zoom = 0f
            if (isRecording) Camera2Helper.zoomWhenRecord(zoom)
            else Camera2Helper.zoomWhenPreview(zoom)
        }

        binding.pictureTv.setOnClickListener {
            binding.pictureTv.isClickable = false
            binding.videoTv.isClickable = true
            binding.pictureTv.setTextColor(getColor(R.color.black))
            binding.videoTv.setTextColor(getColor(R.color.gray))
            binding.ratioBtn.visibility = View.VISIBLE
            binding.recoderBtn.visibility = View.GONE
            binding.takePhotoBtn.visibility = View.VISIBLE
        }

        binding.videoTv.setOnClickListener {
            lifecycleScope.launch {
                updateRatio("9:16")
                Camera2Helper.updateRatio(
                    Size(9, 16),
                    binding.preview.surfaceTexture,
                    ContextCompat.getMainExecutor(this@CameraActivity)
                )
            }
            binding.videoTv.isClickable = false
            binding.pictureTv.isClickable = true
            binding.videoTv.setTextColor(getColor(R.color.black))
            binding.pictureTv.setTextColor(getColor(R.color.gray))
            binding.ratioBtn.visibility = View.GONE
            binding.takePhotoBtn.visibility = View.GONE
            binding.recoderBtn.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun updateRatio(ratio: String) =
        ConstraintSet().run {
            clone(binding.previewParent)
            setDimensionRatio(R.id.preview, ratio)
            applyTo(binding.previewParent)
            this@CameraActivity.ratio = ratio
            binding.ratioBtn.text = ratio
        }

    private fun checkPermission() {
        PermissionX.init(this).permissions(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        ).request { allGranted, grantedList, deniedList ->
            this.allGranted = allGranted
            if (!allGranted) {
                Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG)
                    .show()
            }
            if (allGranted && available) {
                startPreview()
            }
        }
    }

    private fun startPreview() {
        lifecycleScope.launch(Dispatchers.IO) {
            val cameraManager =
                ContextCompat.getSystemService(this@CameraActivity, CameraManager::class.java)
            if (cameraManager == null) {
                Log.e(TAG, "onResume: get CameraManager is null")
                return@launch
            }
            Camera2Helper.preview(
                cameraManager,
                binding.preview.surfaceTexture,
                ContextCompat.getMainExecutor(this@CameraActivity)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Camera2Helper.release()
    }
}