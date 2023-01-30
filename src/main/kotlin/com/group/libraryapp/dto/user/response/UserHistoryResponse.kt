package com.group.libraryapp.dto.user.response

data class UserHistoryResponse(
    val name: String,
    val books: List<BookHistoryResponse>,
)

data class BookHistoryResponse(
    val isReturn: Boolean,
    val name: String,
)
