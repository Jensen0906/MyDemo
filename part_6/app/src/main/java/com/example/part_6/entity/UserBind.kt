package com.example.part_6.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.part_6.BR

class UserBind: BaseObservable() {

    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    @get:Bindable
    var id: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    override fun toString(): String {
        return "{id = $id, name = $name}"
    }
}