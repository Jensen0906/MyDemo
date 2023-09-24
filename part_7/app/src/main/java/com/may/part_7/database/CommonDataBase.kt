package com.may.part_7.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.may.part_7.application.MyApplication
import com.may.part_7.entity.User

@Database(version = 3, entities = [User::class])
abstract class CommonDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        val commDb: CommonDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(MyApplication.appContext, CommonDataBase::class.java, "part7.db")
                .allowMainThreadQueries().build()
        }
    }
}