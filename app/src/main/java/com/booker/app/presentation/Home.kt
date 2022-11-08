package com.booker.app.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.booker.app.domain.model.Book
import com.booker.app.domain.model.Response
import com.booker.app.presentation.component.BookItem
import com.booker.app.viewModel.BookViewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun Home(navController: NavController) {

    val bookViewModel = BookViewModel()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar() {
                Text(text = "Liste des livres")
            }
        },
        floatingActionButton = {
            IconButton(
                onClick = { navController.navigate("create") },
                modifier = Modifier
                    .background(MaterialTheme.colors.secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add  button",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        when (val booksResponse = bookViewModel.getAllBookResponse) {
            is Response.Loading -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {
                println(booksResponse.data)
                if (booksResponse.data.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Aucun livre disponible")
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        LazyColumn() {
                            items(items = booksResponse.data) { book ->
                                BookItem(
                                    book = book,
                                    onDelete = {
                                        book.id?.let {
                                            bookViewModel.deleteBook(
                                                it
                                            )
                                        }
                                    },
                                    onUpdate = {
                                        navController.navigate("update/${book.id}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
            is Response.Error -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = booksResponse.message)
                }
            }
        }
    }

    when (val deleteBookResponse = bookViewModel.deleteBookResponse) {
        is Response.Loading -> {}
        is Response.Success -> {
            Toast.makeText(context, deleteBookResponse.data, Toast.LENGTH_SHORT)
                .show()
        }
        is Response.Error -> {
            Toast.makeText(
                context,
                "erreur survenu lors de la suppression",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

}