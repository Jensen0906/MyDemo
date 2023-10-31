package com.may.part_10.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.may.part_10.base.BaseActivity
import com.may.part_10.adapter.BooksApapter
import com.may.part_10.databinding.ActivityMainBinding
import com.may.part_10.entity.Book
import com.may.part_10.viewmodels.BookViewModel

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val booksAdapter = BooksApapter()
        binding.booksRecyclerview.layoutManager = LinearLayoutManager(this)

        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        bookViewModel.loadBooks()
        bookViewModel.books.observe(this) {
            booksAdapter.mBookList = it
            binding.booksRecyclerview.adapter = booksAdapter
        }

    }

    override fun setDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}