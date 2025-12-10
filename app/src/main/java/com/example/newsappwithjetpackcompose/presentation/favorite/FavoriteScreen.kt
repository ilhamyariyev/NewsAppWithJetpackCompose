package com.example.newsappwithjetpackcompose.presentation.favorite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsappwithjetpackcompose.R
import com.example.newsappwithjetpackcompose.presentation.navigation.BottomBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteScreen(
    navController: NavController,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
){
    Scaffold(

        modifier = Modifier.fillMaxSize(), bottomBar = {
            BottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = onItemSelected,
                navController = navController
            )
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Tabbar()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Tabbar() {
    Column(
        Modifier
            .padding(20.dp)
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ellipse_black),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(18.dp)
            )
            Text(
                "News Catcher", fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.inter_bold))
            )
        }
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
        val formattedDate = LocalDate.now().format(formatter)
        Text(
            formattedDate, fontSize = 13.sp, color = Color(0xFF89969C)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewFavorite(){
    FavoriteScreen(rememberNavController(),0,{})
}