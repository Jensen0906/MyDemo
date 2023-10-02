package com.may.part_8

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.may.part_8.databinding.ActivityMainBinding
import com.may.part_8.network.ApiService
import com.may.part_8.network.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    @Inject lateinit var teamManager: TeamManager
    @Inject @GitHubApi2 lateinit var teamApiService: ApiService

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val job = Job()
        binding.btnGetInfo.setOnClickListener {
            val team = teamManager.getTeam()
            binding.team = team

           // binding.tvShow2.text = "Is ALGS Team: ${teamManager.isALGSTeam()}"

            CoroutineScope(job).launch(Dispatchers.Main) {
                binding.tvShow2.text = "please wait, loading..."
                val result = withContext(Dispatchers.IO) {
                    try {
                        return@withContext teamApiService.getAllUsers()[0].toString()
                    } catch (e: IOException) {
                        Log.e(TAG, "onCreate: ", )
                        return@withContext "network error"
                    }
                }
                binding.tvShow2.text = result
            }
        }
    }
}