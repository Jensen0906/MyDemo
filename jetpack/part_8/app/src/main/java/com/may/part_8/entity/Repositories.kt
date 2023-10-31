package com.may.part_8.entity

class Repositories {
    var id: String? = null
    var node_id = 0
    var name: String? = null
    var full_name: String? = null

    override fun toString(): String {
        return "Repositories(id=$id, node_id=$node_id, name=$name, full_name=$full_name)"
    }
}