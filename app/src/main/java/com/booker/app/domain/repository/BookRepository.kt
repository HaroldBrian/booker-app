package com.booker.app.domain.repository

import com.booker.app.domain.model.Book
import com.booker.app.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun addBook(book: Book) : Flow<Response<String>>

    fun getAllBook() : Flow<Response<List<Book>>>

    fun getOneBook(bookId: String) : Flow<Response<Book?>>

    fun deleteBook(bookId: String) : Flow<Response<String>>

    fun updateBook(book: Book) : Flow<Response<Void>>
}