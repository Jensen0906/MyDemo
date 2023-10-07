package com.may.part_10.repository

import androidx.lifecycle.MutableLiveData
import com.may.part_10.base.BaseRepository
import com.may.part_10.entity.Book
import com.may.part_10.utils.RetrofitService

class BookRepository : BaseRepository() {

    private val api = RetrofitService.getApi()

    suspend fun loadBooks(books: MutableLiveData<ArrayList<Book>?>) {
        execute({ api.getAllBooks() }, books, "?", "???")
    }
}