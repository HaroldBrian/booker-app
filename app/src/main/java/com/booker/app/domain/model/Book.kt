package com.booker.app.domain.model

import com.google.firebase.firestore.DocumentId

data class Book(
    @DocumentId
    var id: String? = null,
    var title: String = "",
    var date: Long = System.currentTimeMillis(),
    var author: String = "",
    var description: String = "",
)
