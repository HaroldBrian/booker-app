package com.booker.app.presentation

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.booker.app.domain.model.Book
import com.booker.app.domain.model.Response
import com.booker.app.viewModel.BookViewModel

@Composable
fun UpdateBook(navController: NavController, idBook: String) {

    val bookViewModel = BookViewModel()
    val context = LocalContext.current

    bookViewModel.getOneBook(idBook)

    Scaffold(topBar = {
        TopAppBar() {
            Text("mis à jour du livre")
        }
    }) { padding ->
        when (val getOneBookResponse = bookViewModel.getOneBookResponse) {
            is Response.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {
                val data = getOneBookResponse.data
                var title by remember { mutableStateOf(TextFieldValue(data!!.title)) }
                var author by remember { mutableStateOf(TextFieldValue(data!!.author)) }
                var description by remember { mutableStateOf(TextFieldValue(data!!.description)) }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(state = ScrollState(20))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(value = title,
                            onValueChange = { title = it },
                            label = { Text("Titre du livre") },
                            placeholder = { Text("titre") }
                        )
                        TextField(value = author,
                            onValueChange = { author = it },
                            label = { Text("Nom de l'auteur") },
                            placeholder = { Text("auteur") }
                        )
                        TextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description de l'auteur") },
                            placeholder = { Text("Description") },
                            maxLines = 3
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(.8f),
                            onClick = {
                                bookViewModel.updateBook(
                                    Book(
                                        id = idBook,
                                        title = title.text,
                                        author = author.text,
                                        description = description.text
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    "Livre mis à jour avec succès",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("home")
                            },
                        ) {
                            Text("MODIFIER")
                        }
                    }
                }
            }
            is Response.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error")
                }
            }

        }
    }
}

