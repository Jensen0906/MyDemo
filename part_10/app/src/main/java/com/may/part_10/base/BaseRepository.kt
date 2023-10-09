package com.may.part_10.base

import androidx.lifecycle.MutableLiveData
import com.example.toast.Toast
import com.may.part_10.App.Companion.appContext
import com.may.part_10.R
import com.may.part_10.constant.Constants.NetWorkConst.SUCCESS_STATUS
import com.may.part_10.network.ApiResult
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T> execute(
        block: suspend () -> ApiResult<T>,
        response: MutableLiveData<T>,
    ) {
        try {
            val result = block.invoke()
            if (result.status == SUCCESS_STATUS) {
                response.postValue(result.data)
            } else {
                result.msg?.let {
                    Toast.error(appContext, it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.error(appContext, appContext.getString(R.string.connect_server_failed))
        }
    }
}