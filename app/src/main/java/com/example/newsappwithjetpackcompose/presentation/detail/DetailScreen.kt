package com.example.newsappwithjetpackcompose.presentation.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsappwithjetpackcompose.R
import com.example.newsappwithjetpackcompose.data.dto.Article

@Composable
fun DetailScreen(
    navController: NavController,
    article: Article,
    viewModel: DetailViewModel = hiltViewModel(),
    localizedContext: Context
) {
    val allFavorites by viewModel.allFavorites.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val isSaved = remember(article.url, allFavorites) {
        val url = article.url ?: return@remember false
        allFavorites.any { it.url == url }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .padding(horizontal = 30.dp, vertical = 24.dp)
                ) {

                    Text(
                        text = article.source?.name
                            ?: localizedContext.getString(R.string.there_is_no_source),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = article.title
                            ?: localizedContext.getString(R.string.there_is_no_title),
                        fontSize = 26.sp,
                        fontFamily = FontFamily(Font(R.font.inter_bold))
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = article.author
                            ?: localizedContext.getString(R.string.there_is_no_author),
                        color = Color(0xFF0AA7FF),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = article.description
                            ?: localizedContext.getString(R.string.there_is_no_description),
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.inter_bold))
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = article.content
                            ?: localizedContext.getString(R.string.there_is_no_content),
                        fontSize = 12.sp,
                        color = Color(0xFF89969C),
                        fontFamily = FontFamily(Font(R.font.inter_medium))
                    )

                    Text(
                        text = localizedContext.getString(R.string.read_more),
                        color = Color(0xFF57A5D1),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .align(Alignment.End)
                            .clickable {
                                article.url?.let { url ->
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Log.e("DetailScreen", "URL açılmadı: $url")
                                    }
                                }
                            })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(Color(0xD7DADADA))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
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
                    text = article.title?.take(15)?.plus("...")
                        ?: localizedContext.getString(R.string.x_b_r_ba_l),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            ThreeDotMenu(
                article = article, isSaved = isSaved, onToggle = { shouldSave ->
                    if (shouldSave) {
                        viewModel.toggleFavorite(article)
                        Toast.makeText(
                            context,
                            localizedContext.getString(R.string.saved),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.toggleFavorite(article)
                        Toast.makeText(
                            context,
                            localizedContext.getString(R.string.unsaved),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, localizedContext = localizedContext, navController = navController
            )
        }
    }
}


@Composable
fun ThreeDotMenu(
    article: Article,
    isSaved: Boolean,
    onToggle: (Boolean) -> Unit,
    localizedContext: Context,
    navController: NavController
) {
    var showMenu by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            showMenu = false
        }
    }

    Box {

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xD7DADADA))
                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
                    showMenu = true
                }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                tint = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (showMenu) {
            Popup(
                alignment = Alignment.TopEnd, onDismissRequest = { showMenu = false }) {

                Column(
                    modifier = Modifier
                        .padding(end = 10.dp, top = 55.dp)
                        .shadow(12.dp, RoundedCornerShape(22.dp), clip = false)
                        .background(Color.White, RoundedCornerShape(22.dp))
                        .width(170.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                val newValue = !isSaved
                                onToggle(newValue)
                                showMenu = false
                            }
                            .padding(vertical = 14.dp, horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = if (isSaved) painterResource(R.drawable.save_filled)
                            else painterResource(R.drawable.save),
                            contentDescription = if (isSaved) "Unsave" else "Save",
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = if (isSaved) localizedContext.getString(R.string.unsave) else localizedContext.getString(
                                R.string.save
                            ),
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            fontSize = 16.sp
                        )
                    }


                    HorizontalDivider(
                        thickness = 1.dp, color = Color.Black
                    )

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val NewsUrl = article.url
                            val shareText = """
                  *${article.title}*
        
                 
        
                  📝 Description:
                   ${article.description}
        
                   📱 Click Link for more
                    Link: $NewsUrl
                   """.trimIndent()

                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }

                            val shareIntent = Intent.createChooser(sendIntent, "Share News")
                            context.startActivity(shareIntent)
                            showMenu = false
                        }
                        .padding(vertical = 14.dp, horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF1E88E5),
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            localizedContext.getString(R.string.share),
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

