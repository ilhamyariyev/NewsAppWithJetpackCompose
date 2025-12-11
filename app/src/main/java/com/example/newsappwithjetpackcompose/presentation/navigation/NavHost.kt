package com.example.newsappwithjetpackcompose.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsappwithjetpackcompose.data.dto.Article
import com.example.newsappwithjetpackcompose.presentation.detail.DetailScreen
import com.example.newsappwithjetpackcompose.presentation.favorite.FavoriteScreen
import com.example.newsappwithjetpackcompose.presentation.home.HomeScreen
import com.example.newsappwithjetpackcompose.presentation.home.HomeViewModel
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(localizedContext: Context) {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        selectedIndex = when (currentRoute) {
            Screen.Home::class.qualifiedName -> 0
            Screen.Favorite::class.qualifiedName -> 1
            else -> selectedIndex
        }
    }
    NavHost(
        navController = navController, startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                navController = navController,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it },
                viewModel = homeViewModel,
                localizedContext = localizedContext
            )
        }
        composable<Screen.Favorite> {
            FavoriteScreen(
                navController = navController,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it },
                localizedContext = localizedContext
            )
        }
        composable<Screen.Detail>(typeMap = mapOf(typeOf<Article>() to navTypeOf<Article>())) {
            val args = it.toRoute<Screen.Detail>()
            val article = args.article

            DetailScreen(
                navController = navController,
                article = article,
                localizedContext = localizedContext
            )
        }
    }
}


