package com.may.part_10.network

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class ApiResult<T>(
    var status: Int = -1,
    var msg: String? = null,
    var data: T? = null
)
