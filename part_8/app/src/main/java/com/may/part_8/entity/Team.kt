package com.may.part_8.entity

class Team(var id: Int, var name: String, var slogan: String) {
    override fun toString(): String {
        return "Team Name: $name\nSlogan: $slogan"
    }
}
