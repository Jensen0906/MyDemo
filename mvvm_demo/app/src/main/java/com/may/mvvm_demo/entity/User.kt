package com.may.mvvm_demo.entity

class User{
    var account: String = ""
    var pwd: String = ""

    constructor(account: String, pwd: String) {
        this.account = account
        this.pwd = pwd
    }

    constructor()
}