package com.example.newsappwithjetpackcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.newsappwithjetpackcompose.presentation.home.HomeViewModel
import com.example.newsappwithjetpackcompose.presentation.navigation.NavHost
import com.example.newsappwithjetpackcompose.ui.theme.NewsAppWithJetpackComposeTheme
import com.example.newsappwithjetpackcompose.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val languageViewModel: HomeViewModel by viewModels()

    companion object {
        val LocalAppLocale = staticCompositionLocalOf { "EN" }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val lang = languageViewModel.currentLang.collectAsState("EN").value

            val context = LocalContext.current
            val localizedContext = remember(lang) { LocaleHelper.updateLocale(context, lang) }

            CompositionLocalProvider(LocalAppLocale provides lang) {
                NewsAppWithJetpackComposeTheme {
                    NavHost(localizedContext)
                }
            }

        }
    }
}
