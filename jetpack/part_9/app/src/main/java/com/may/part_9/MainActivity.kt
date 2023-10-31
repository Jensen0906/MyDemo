package com.may.part_9

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.may.part_9.databinding.ActivityMainBinding
import com.may.part_9.network.ApiService
import com.may.part_9.network.UserBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*        //GlobalScope.launch
                Log.d(TAG, " start delay ")
                val job = GlobalScope.launch(Dispatchers.Unconfined) {
                    delay(5000L)
                    Log.d(TAG, "Thread This: ${Thread.currentThread().name}")
                }
                binding.btnStop.setOnClickListener {
                    job.cancel()
                }*/

        val job = Job()

        /*        //CoroutineScope(job).launch
                Log.d(TAG, "----start delay----")
                CoroutineScope(job).launch {
                    delay(5000L)
                    Log.d(TAG, "print this message after delay 5s (CoroutineScope)")
                }
                job.cancel()*/
        /*        //必须在协程作用域中调用的async
                Log.d(TAG, "----start delay----")
                CoroutineScope(job).launch {
                    val result = async {
                        delay(5000L)
                        val test = ArrayList<String>()
                        test.add("print this message after delay 5s")
                        test
                    }.await()
                    Log.d(TAG, result[0])
                }*/
        /*        //await阻塞行为
                CoroutineScope(job).launch {
                    val startTime = System.currentTimeMillis()
                    val result1 = async {
                        delay(5000L)
                        return@async "result1"
                    }

                    val result2 = async {
                        delay(3000L)
                        return@async "result2"
                    }
                  //  Log.d(TAG, "--$result1-- || --$result2--")
                    Log.d(TAG, "-- ${result1.await()}-- || --${result2.await()} --")
                    val endTime = System.currentTimeMillis()
                    Log.d(TAG, "-- execute time: ${endTime - startTime} --")
                }*/
        /*        //why use Coroutine?
                CoroutineScope(job).launch(Dispatchers.Main) {
                    val data1 = loadData(1)
                    binding.tvShow.text = data1
                    val data2 = loadData(2)
                    binding.tvShow2.text = data2
                }*/

/*        loadDataLoser()*/


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create<ApiService>()
/*        apiService.queryDataLoser().enqueue(object : Callback<List<UserBean>> {
            override fun onResponse(
                call: Call<List<UserBean>>,
                response: Response<List<UserBean>>
            ) {
                if (response.body() != null) {
                    binding.tvShow.text = response.body()!![0].toString()
                } else {
                    binding.tvShow2.let {
                        it.text = "Ohuo, I don't get any thing"
                        it.setTextColor(getColor(R.color.warning))
                        it.textSize = 22f
                    }
                }
            }

            override fun onFailure(call: Call<List<UserBean>>, t: Throwable) {
                binding.tvShow2.let {
                    it.text = "Ohuo, Faild to connect network"
                    it.setTextColor(getColor(R.color.error))
                    it.textSize = 22f
                }
                t.printStackTrace()
            }

        })*/

        CoroutineScope(job).launch(Dispatchers.Main) {

            try {
                val result = withContext(Dispatchers.IO) {
                    apiService.queryData()
                }
                binding.tvShow.text = result[0].toString()
            } catch (e: IOException) {
                binding.tvShow2.let {
                    it.text = "Ohuo, Faild to connect network"
                    it.setTextColor(getColor(R.color.error))
                    it.textSize = 22f
                }
                e.printStackTrace()
            } finally {
                Toast.makeText(this@MainActivity, "over!", Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun loadDataLoser() {
        Thread {
            Thread.sleep(2000L)//模拟耗时操作
            runOnUiThread {
                binding.tvShow.text = "get data1 successfully"
                loadData2Loser()
            }
        }.start()
    }
    private fun loadData2Loser() {
        Thread {
            Thread.sleep(2000L)//模拟耗时操作
            runOnUiThread {
                binding.tvShow2.text = "get data2 successfully"
            }
        }.start()
    }

    /**
     * 挂起函数 示例
     * @param need which data u need?
     * @return yes, which data u need!
     * @author Jensen
     */
    private suspend fun loadData(need: Int) =
        withContext(Dispatchers.IO) {
            delay(2000L)//模拟耗时操作
            val datas = ArrayList<String>()
            datas.add("get data1 successfully")
            datas.add("get data2 successfully")
            datas[need - 1]
        }
}