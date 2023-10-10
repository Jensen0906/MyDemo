package com.may.part_10.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.may.part_10.BR

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class User : BaseObservable() {
    var id = 0
    @get: Bindable
    var username: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }
    @get: Bindable
    var password: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }
    var userStatus = 0

    override fun toString(): String {
        return "User(id=$id, username=$username, password=$password, userStatus=$userStatus)"
    }
}