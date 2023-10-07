package com.may.part_10.base

import androidx.lifecycle.MutableLiveData
import com.example.toast.Toast
import com.may.part_10.App.Companion.appContext
import com.may.part_10.constant.Constants.NetWorkConst.SUCCESS_STATUS
import com.may.part_10.entity.Book
import com.may.part_10.network.ApiResult
import java.io.IOException

abstract class BaseRepository<T> {

    suspend fun execute(
        block: suspend () -> ApiResult<T>,
        response: MutableLiveData<T>,
        dataError: String,
        executeError: String
    ) {
        try {
            val result = block.invoke()
            if (result.status == SUCCESS_STATUS) {
                response.postValue(result.data)
            } else {
                Toast.error(appContext, dataError)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.error(appContext, executeError)
        }
    }

    suspend fun executeList(
        block: suspend () -> ApiResult<ArrayList<T>?>,
        response: MutableLiveData<ArrayList<T>?>,
        dataError: String,
        executeError: String
    ) {
        try {
            val result = block.invoke()
            if (result.status == SUCCESS_STATUS) {
                response.postValue(result.data)
            } else {
                Toast.error(appContext, dataError)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.error(appContext, executeError)
        }
    }

}