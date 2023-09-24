package com.may.part_7.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "book_name") var bookName: String,
    @ColumnInfo(name = "book_code") var bookCode: String
)
