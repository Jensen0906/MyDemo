package com.may.part_9.network

class UserBean {
    var login: String? = null
    var id = 0
    var node_id: String? = null
    var avatar_url: String? = null

    override fun toString(): String {
        return "UserBean(login=$login, id=$id, node_id=$node_id, avatar_url=$avatar_url)"
    }
}