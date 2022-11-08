package com.booker.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.booker.app.presentation.CreateBook
import com.booker.app.presentation.Home
import com.booker.app.presentation.UpdateBook
import com.booker.app.ui.theme.BookerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable(route = "home") {
                            Home(navController = navController)
                        }
                        composable(route = "create") {
                            CreateBook(navController = navController)
                        }
                        composable(
                            route = "update/{bookId}", arguments = listOf(
                                navArgument("bookId") {
                                    type = NavType.StringType
                                    nullable = false
                                })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("bookId")
                            UpdateBook(navController = navController, idBook = id!!)
                        }
                    }
                }
            }
        }
    }
}
