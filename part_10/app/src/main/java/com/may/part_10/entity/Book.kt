package com.may.part_10.entity

class Book {
    var id: Int = 0
    var name: String? = null
    var author: String? = null
    var isbn: String? = null
    var type: Int = 0
    var price: Float = 0.00f
    var alive: Boolean = true
    var introduction: String? = null

    override fun toString(): String {
        return "Book(name=$name, author=$author, price=$price)"
    }
}