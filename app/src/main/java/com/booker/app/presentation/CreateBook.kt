package com.booker.app.presentation

import android.Manifest
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.booker.app.domain.model.Book
import com.booker.app.domain.model.Response
import com.booker.app.viewModel.BookViewModel
import com.himanshoe.pluck.PluckConfiguration
import com.himanshoe.pluck.ui.Pluck
import com.himanshoe.pluck.ui.permission.Permission

@Composable
fun CreateBook(navController: NavController) {

    val bookViewModel = BookViewModel()
    val context = LocalContext.current
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var author by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var enabled by remember {
        mutableStateOf(true)
    }

    Scaffold(topBar = {
        TopAppBar() {
            Text("Ajout du livre", textAlign = TextAlign.Center)
        }
    }) { padding ->
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
                        bookViewModel.addBook(
                            Book(
                                title = title.text,
                                author = author.text,
                                description = description.text
                            )
                        )
                    },
                ) {
                    Text("AJOUTER")
                }

                when (val addBookResponse = bookViewModel.addBookResponse) {
                    is Response.Loading -> enabled = false
                    is Response.Success -> {
                        Toast.makeText(context, "livre Ajouté avec succès", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate("home")
                    }
                    is Response.Error -> {
                        Toast.makeText(
                            context,
                            "erreur survenu lors de l'enregistrement",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        enabled = true
                    }
                }
            }
        }
    }
}

