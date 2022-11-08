package com.booker.app.domain.repository

import com.booker.app.domain.model.Book
import com.booker.app.domain.model.Response
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BookRepositoryIMpl : BookRepository {
    private val dbRef = Firebase.firestore.collection("book")
    override fun addBook(book: Book) = callbackFlow {
        var response: Response<String> = Response.Loading
        dbRef.add(book).addOnSuccessListener {
            response = Response.Success(it.id)
            trySend(response)
        }.addOnFailureListener {
            response = Response.Error(it.message ?: it.toString())
        }

        awaitClose {
            dbRef
        }
    }

    override fun getAllBook() = callbackFlow {
        val snapshotListener =
            dbRef.orderBy("author").addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val books = snapshot.toObjects(Book::class.java)
                    Response.Success(books)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getOneBook(bookId: String): Flow<Response<Book?>> = callbackFlow {
        val snapshotListener = dbRef.document(bookId).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val book = snapshot.toObject(Book::class.java)
                Response.Success(book)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun deleteBook(bookId: String) = flow {
        try {
            emit(Response.Loading)
            dbRef.document(bookId).delete().await()
            emit(Response.Success("Livre supprimé avec succès"))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun updateBook(book: Book): Flow<Response<Void>> = flow {
        try {
            println(book)
            emit(Response.Loading)
            val id = book.id
            val response = dbRef.document(id!!).set(book).await()
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}