package com.may.part_10.network

class ApiResult<T>(
    var status: Int = -1,
    var msg: String? = null,
    var data: T? = null
)
