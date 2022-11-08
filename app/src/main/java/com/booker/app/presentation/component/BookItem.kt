package com.booker.app.presentation.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.booker.app.R
import com.booker.app.domain.model.Book
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookItem(book: Book, onDelete: () -> Unit, onUpdate: () -> Unit) {
    val context = LocalContext.current
    Card(
        elevation = 10.dp, modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(3.dp)) {
            Column() {
                Text(text = book.title, style = TextStyle(fontWeight = FontWeight.ExtraBold))
                Text(text = book.author, style = TextStyle(fontWeight = FontWeight.SemiBold))
                Text(text = book.description, overflow = TextOverflow.Ellipsis)
                Text(text = formatDate(book.date), style = TextStyle(fontWeight = FontWeight.Thin))
            }
            Column() {
                IconButton(onClick = {
                    onDelete()
                    Toast.makeText(
                        context,
                        "livre supprimé avec succès",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                }
                IconButton(onClick = {
                    onUpdate()
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "update")
                }
            }

        }

    }
}


fun formatDate(date: Long): String {
    val date = Date(date)
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    return sdf.format(date)
}