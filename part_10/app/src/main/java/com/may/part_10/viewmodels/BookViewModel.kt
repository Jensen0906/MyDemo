package com.may.part_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.part_10.entity.Book
import com.may.part_10.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val TAG = "BookViewModel"

    private var _books = MutableLiveData<ArrayList<Book>?>()
    val books: LiveData<ArrayList<Book>?> = _books

    private val repository = BookRepository()

    fun loadBooks() {
        viewModelScope.launch {
            repository.loadBooks(_books)
        }
    }
}