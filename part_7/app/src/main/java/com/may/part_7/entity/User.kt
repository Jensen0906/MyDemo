package com.may.part_7.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "username") var username: String,
    var password: String
) {
    override fun toString(): String {
        return "[ username=$username, password=$password ]"
    }
}