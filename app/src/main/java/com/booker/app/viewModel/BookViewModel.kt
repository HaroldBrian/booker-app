package com.booker.app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booker.app.domain.model.Book
import com.booker.app.domain.model.Response
import com.booker.app.domain.repository.BookRepositoryIMpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private var _repository = BookRepositoryIMpl()
    var getAllBookResponse by mutableStateOf<Response<List<Book>>>(Response.Loading)
        private set

    var addBookResponse by mutableStateOf<Response<String>>(Response.Loading)
        private set

    var deleteBookResponse by mutableStateOf<Response<String>>(Response.Loading)
        private set

    var getOneBookResponse by mutableStateOf<Response<Book?>>(Response.Loading)
        private set

    var updateBookResponse by mutableStateOf<Response<Void?>>(Response.Loading)
        private set

    init {
        getAllBok()
    }

    fun addBook(book: Book) = viewModelScope.launch {
        _repository.addBook(book).collect {
            addBookResponse = it
        }
    }

    private fun getAllBok() = viewModelScope.launch {
        _repository.getAllBook().collect {
            getAllBookResponse = it
        }
    }

    fun deleteBook(bookId: String) = viewModelScope.launch {
        _repository.deleteBook(bookId).collect {
            deleteBookResponse = it
        }
    }

    fun getOneBook(bookId: String) = viewModelScope.launch {
        _repository.getOneBook(bookId).collect {
            getOneBookResponse = it
        }
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        _repository.updateBook(book).collect {
            updateBookResponse = it
        }
    }
}