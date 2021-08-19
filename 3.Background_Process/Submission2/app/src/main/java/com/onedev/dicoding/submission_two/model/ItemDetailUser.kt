package com.onedev.dicoding.submission_two.model

data class ItemDetailUser(
    val avatar_url: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val public_repos: Int,
    val type: String,
    val url: String
)