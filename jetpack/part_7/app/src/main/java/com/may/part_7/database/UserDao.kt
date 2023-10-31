package com.may.part_7.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.may.part_7.entity.User

@Dao
interface UserDao {
    @Insert(entity = User::class)
    fun register(user: User)

    @Query("Select * from User where username == :username and password == :password")
    fun login(username: String, password: String): User?

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}