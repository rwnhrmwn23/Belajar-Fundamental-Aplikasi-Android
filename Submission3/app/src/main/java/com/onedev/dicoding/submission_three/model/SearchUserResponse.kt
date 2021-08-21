package com.onedev.dicoding.submission_three.model

data class SearchUserResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<ItemUser>,
    val total_count: Int
)