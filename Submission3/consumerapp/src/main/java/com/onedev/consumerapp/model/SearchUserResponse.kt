package com.onedev.consumerapp.model

data class SearchUserResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<ItemUser>,
    val total_count: Int
)