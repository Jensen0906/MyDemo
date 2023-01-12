package com.may.mvvm_demo.entity

import androidx.databinding.BaseObservable

class User : BaseObservable{
    var account: String = ""
    var pwd: String = ""

    constructor(account: String, pwd: String) {
        this.account = account
        this.pwd = pwd
    }

    constructor()
}