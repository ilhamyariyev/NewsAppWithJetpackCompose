package com.example.newsappwithjetpackcompose.presentation.favorite

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movawithjetpackcompose.domain.model.toArticle
import com.example.newsappwithjetpackcompose.R
import com.example.newsappwithjetpackcompose.presentation.navigation.BottomBar
import com.example.newsappwithjetpackcompose.presentation.navigation.Screen
import com.info.androidileriders2.roomDB.Favorite
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteScreen(
    navController: NavController,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
    localizedContext: Context
) {

    val favorites by viewModel.allFavorites.collectAsStateWithLifecycle()

    Scaffold(

        modifier = Modifier.fillMaxSize(), containerColor = Color.White, bottomBar = {
            BottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = onItemSelected,
                navController = navController
            )
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Tabbar(localizedContext)
            if (favorites.isEmpty()) {
                ShowEmptyMyNews(localizedContext)
            } else {
                MyListNewsRow(favorites, navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Tabbar(localizedContext: Context) {
    Column(
        Modifier
            .padding(16.dp)
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
                localizedContext.getString(R.string.news_catcher),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.inter_bold))
            )
        }
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
        val formattedDate = LocalDate.now().format(formatter)
        Text(
            formattedDate, fontSize = 13.sp, color = Color(0xFF89969C)
        )
    }
}

@Composable
fun ShowEmptyMyNews(localizedContext: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(R.drawable.empty_favorite),
            contentDescription = "",
            Modifier.size(120.dp)
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = localizedContext.getString(R.string.your_favorite_news_is_empty),
            fontSize = 24.sp,
            color = Color.Red,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyListNewsRow(favorites: List<Favorite>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(favorites) { favorite ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate(Screen.Detail(favorite.toArticle()))
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    favorite.title?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_bold)),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                    Row(modifier = Modifier.padding(top = 5.dp)) {
                        favorite.publishedAt?.let {
                            Text(
                                text = it.take(10),
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.inter_medium)),
                                color = Color(0xFF89969C),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        favorite.author?.let {
                            val displayAuthor = if (it.length > 15) it.take(15) + "…" else it
                            Text(
                                text = displayAuthor,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter_bold)),
                                color = Color.Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier
                        .width(102.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    val image = favorite.urlToImage
                    if (image.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.gray_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.matchParentSize()
                        )
                    } else {
                        AsyncImage(
                            model = image,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }
            }
        }

    }

}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun PreviewFavorite(){
//ShowEmptyMyNews()
//}