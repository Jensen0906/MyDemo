package com.may.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.toast.Toast
import com.may.chatroom.application.ChatRoomApp.Companion.context
import com.may.chatroom.databinding.ActivityMainBinding
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isConnect = false
    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        clickEvent()
    }

    private fun initView() {}

    private fun clickEvent() {
        binding.connect.setOnClickListener {
            Toast.info(context, "connecting..")
            connectServer()
        }
        binding.send.setOnClickListener {
            val msg = binding.msgSend.text.toString()
            if (socket == null) {
                Toast.warning(context, String.format(getString(R.string.connect_failed), msg))
            } else if (msg.isNotEmpty()) {
                try {
                    val outputStream = DataOutputStream(socket!!.getOutputStream())
                    outputStream.writeUTF(msg)
                    Toast.success(context, String.format(getString(R.string.send_success), msg))
                    Log.e(TAG, "send message: $msg")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun connectServer() {
        try {
            socket = Socket("http://124.221.10.82/", 8084)
            Toast.success(context, "connect success")
            Log.e(TAG, "connectServer")
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: UnknownError) {
            e.printStackTrace()
        }
    }

    companion object {
        const val TAG = "May"
    }
}