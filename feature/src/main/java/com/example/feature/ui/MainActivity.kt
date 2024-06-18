package com.example.feature.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.models.ContactsItemUiModel
import com.example.feature.ui.detail.DetailScreen
import com.example.feature.ui.home.HomeScreen
import com.example.feature.ui.largeImage.LargeImageScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    fun Content() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen {
                    navController.currentBackStackEntry?.savedStateHandle?.set("contactUiModel", it)
                    navController.navigate("details")
                }
            }
            composable("details") {
                val contactUiModel =
                    navController.previousBackStackEntry?.savedStateHandle?.get<ContactsItemUiModel>(
                        "contactUiModel"
                    )
                contactUiModel?.let {
                    DetailScreen(
                        it,
                        onBackClicked = { navController.popBackStack() },
                        onImageClicked = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "contactUiModel",
                                it
                            )
                            navController.navigate("largeImageScreen")
                        })
                }
            }
            composable("largeImageScreen") {
                val contactUiModel =
                    navController.previousBackStackEntry?.savedStateHandle?.get<ContactsItemUiModel>(
                        "contactUiModel"
                    )
                contactUiModel?.let {
                    LargeImageScreen(it.picture?.large)
                }
            }
        }
    }
}