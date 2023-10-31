package com.may.part_10.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.may.part_10.databinding.BooksRecyclerViewItemBinding
import com.may.part_10.entity.Book

/**
 * @Author Jensen
 * @Date 2023/10/08
 */

class BooksApapter : RecyclerView.Adapter<BooksApapter.BookItemViewHolder>() {

    private val TAG = "BooksApapter"

    var mBookList: ArrayList<Book>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookItemViewHolder {
        return BookItemViewHolder(
            BooksRecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (mBookList.isNullOrEmpty()) {
            0
        } else {
            mBookList!!.size
        }
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {

        val mHolder = holder
        val book = mBookList?.get(position)
        Log.d(TAG, "onBindViewHolder: $book")
        book?.let {
            mHolder.dataBinding.book = it
        }
    }

    class BookItemViewHolder(var dataBinding: BooksRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}