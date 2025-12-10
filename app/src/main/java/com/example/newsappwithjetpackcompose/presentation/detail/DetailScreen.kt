package com.example.newsappwithjetpackcompose.presentation.detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsappwithjetpackcompose.R
import com.example.newsappwithjetpackcompose.data.dto.Article

@Composable
fun DetailScreen(
    navController: NavController,
    article: Article
) {

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(Color(0xD7DADADA))
                    .clickable {
                        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                            navController.popBackStack()
                        }
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Geri",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = article.title?.take(15)?.plus("...") ?: "Xəbər Detalı",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            ThreeDotMenu()
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 200.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(horizontal = 30.dp, vertical = 24.dp)
        ) {

            Text(
                text = article.source?.name ?: "Mənbə Yoxdur",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(
                        color = Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = article.title ?: "Başlıq Yoxdur",
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.inter_bold))
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Author
            Text(
                text = article.author ?: "Anonim",
                color = Color(0xFF0AA7FF),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = article.description ?: "Ətraflı məlumat yoxdur.",
                fontSize = 15.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.inter_bold))
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = article.content ?: "Tam mətn mövcud deyil.",
                fontSize = 12.sp,
                color = Color(0xFF89969C),
                fontFamily = FontFamily(Font(R.font.inter_medium))
            )

            Text(
                text = "Read more...",
                color = Color(0xFF57A5D1),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .align(Alignment.End)
                    .clickable { /* Handle click to web browser */ })
        }
    }
}

@Composable
fun ThreeDotMenu() {
    var showMenu by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = {
                showMenu = true
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(25.dp)
                .clip(CircleShape)
                .background(Color(0xFFDADADA))
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                tint = Color.Black
            )
        }

        if (showMenu) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest = { showMenu = false }
            ) {

                Column(
                    modifier = Modifier
                        .padding(end = 10.dp, top = 65.dp)
                        .shadow(12.dp, RoundedCornerShape(22.dp), clip = false)
                        .background(Color.White, RoundedCornerShape(22.dp))
                        .width(170.dp)
                ) {

                    // Save
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMenu = false
                            }
                            .padding(vertical = 14.dp, horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.save),
                            contentDescription = "Save",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text("Save", color = Color.Black)
                    }

                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp
                    )

                    // Share
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMenu = false
                            }
                            .padding(vertical = 14.dp, horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF1E88E5),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text("Share", color = Color.Black)
                    }
                }
            }
        }
    }
}

