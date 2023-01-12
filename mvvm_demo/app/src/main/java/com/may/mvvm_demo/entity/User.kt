package com.may.mvvm_demo.entity

import androidx.databinding.ObservableField

class User {
    var account: ObservableField<String> = ObservableField()
    var pwd: ObservableField<String> = ObservableField()

    constructor()
    constructor(account: String, pwd: String) {
        this.account = ObservableField(account)
        this.pwd = ObservableField(pwd)
    }
}