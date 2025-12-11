package com.example.newsappwithjetpackcompose.presentation.home

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsappwithjetpackcompose.R
import com.example.newsappwithjetpackcompose.data.dto.Article
import com.example.newsappwithjetpackcompose.data.dto.NewsCategory
import com.example.newsappwithjetpackcompose.presentation.navigation.BottomBar
import com.example.newsappwithjetpackcompose.presentation.navigation.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    localizedContext: Context
) {
    val state by viewModel.newsState.collectAsStateWithLifecycle()
    val category by viewModel.categoryState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    val currentLanguage by viewModel.currentLang.collectAsStateWithLifecycle()
    var languageDropdownExpanded by remember { mutableStateOf(false) }
    var currentCategory by remember { mutableStateOf(NewsCategory.BUSINESS) }
    var searchText by remember { mutableStateOf("") }


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
                .verticalScroll(rememberScrollState())
        ) {
            Tabbar(localizedContext)

            if (state.isLoading) {
                ShimmerNewsGridSimple()
            } else {
                state.news?.let { articles ->
                    NewsHorizontal(
                        articles = articles, navController = navController
                    )
                }
            }

            Search(
                text = searchText, onTextChange = { newText ->
                    searchText = newText
                    if (newText.isNotEmpty()) viewModel.getSearchNews(newText)
                }, localizedContext
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CategoryDropdown(
                        selectedCategory = currentCategory, onCategorySelected = { selected ->
                            currentCategory = selected
                            viewModel.getCategoryNews(selected.apiName)
                        })
                }

                Spacer(modifier = Modifier.width(16.dp))

                LanguageButton(
                    currentLanguage = currentLanguage,
                    onClick = { languageDropdownExpanded = true },
                    modifier = Modifier.wrapContentWidth()
                )
            }

            if (category.isLoading) {
                ShimmerCategoryNewsItemSimple()
            } else {
                val articlesToShow = if (searchText.isNotEmpty()) searchState.news.orEmpty()
                else category.news.orEmpty()

                if (articlesToShow.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            localizedContext.getString(R.string.no_articles_found),
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    articlesToShow.forEach { article ->
                        if (searchText.isNotEmpty()) {
                            SearchNewsRow(new = article, navController = navController)
                        } else {
                            CategoryNewsRow(
                                new = article,
                                navController = navController,
                                selectedCategory = currentCategory
                            )
                        }
                    }
                }
            }
        }
        if (languageDropdownExpanded) {
            LanguageDropdown(
                expanded = languageDropdownExpanded,
                currentLanguage = currentLanguage,
                onExpandedChange = { languageDropdownExpanded = it },
                onLanguageSelected = {
                    viewModel.changeLanguage(it)
                    languageDropdownExpanded = false
                },
                localizedContext
            )
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
fun NewsHorizontal(
    articles: List<Article>, navController: NavController
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 20.dp)
    ) {
        items(articles) { article ->
            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .width(320.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate(Screen.Detail(article))
                    }) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, Color.White.copy(alpha = 0.5f)
                                ), startY = 0f, endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                Text(
                    text = article.source?.name ?: "",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = article.title ?: "",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun Search(text: String, onTextChange: (String) -> Unit, localizedContext: Context) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        placeholder = {
            Text(
                localizedContext.getString(R.string.search), color = Color(0xFFE2E2E2)
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .border(1.5.dp, Color(0xFFE2E2E2), RoundedCornerShape(24.dp)),
        keyboardOptions = KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE2E2E2),
            unfocusedBorderColor = Color(0xFFE2E2E2),
            focusedTextColor = Color.Black,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun LanguageButton(
    currentLanguage: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFE8E8E8))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            currentLanguage, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(Color(0xFFC4C4C4)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFAC5151))
            )
        }
    }
}


@Composable
fun LanguageDropdown(
    expanded: Boolean,
    currentLanguage: String,
    onExpandedChange: (Boolean) -> Unit,
    onLanguageSelected: (String) -> Unit,
    localizedContext: Context
) {
    if (!expanded) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, top = 25.dp)
                .width(83.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Color(0xFFE8E8E8))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) { onExpandedChange(false) }, contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(18.dp)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            val languages =
                listOf(localizedContext.getString(R.string.en), stringResource(R.string.ru))
            languages.forEach { lang ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onLanguageSelected(lang)
                            onExpandedChange(true)
                        }
                        .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        lang,
                        fontFamily = FontFamily(Font(R.font.inter_medium)),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 24.dp)
                    )
                    if (lang == currentLanguage) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.Green)
                    }
                }
                HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.Black)
            }
        }
    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: NewsCategory, onCategorySelected: (NewsCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) { expanded = true }
                .background(Color(0xFFE8E8E8), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(selectedCategory.name)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            containerColor = Color.White,
            onDismissRequest = { expanded = false }) {
            NewsCategory.entries.forEach { category ->
                DropdownMenuItem(text = { Text(category.name) }, onClick = {
                    onCategorySelected(category)
                    expanded = false
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CategoryNewsRow(new: Article, navController: NavController, selectedCategory: NewsCategory) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { navController.navigate(Screen.Detail(new)) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(26.dp))
                    .background(Color.LightGray)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = selectedCategory.name,
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 3.dp)
                )
            }
            new.title?.let {
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
                new.publishedAt?.let {
                    Text(
                        text = it.take(10),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium)),
                        color = Color(0xFF89969C),
                        modifier = Modifier.weight(1f)
                    )
                }
                new.author?.let {
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
            val image = new.urlToImage
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchNewsRow(new: Article, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { navController.navigate(Screen.Detail(new)) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            new.title?.let {
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
                new.publishedAt?.let {
                    Text(
                        text = it.take(10),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium)),
                        color = Color(0xFF89969C),
                        modifier = Modifier.weight(1f)
                    )
                }
                new.author?.let {
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
            val image = new.urlToImage
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

@Composable
fun ShimmerGridItem() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing)
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 200f, translateAnim + 200f)
    )
    Box(
        modifier = Modifier
            .size(width = 320.dp, height = 180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush)
    )
}

@Composable
fun ShimmerNewsGridSimple() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(6) { ShimmerGridItem() }
    }
}

@Composable
fun ShimmerCategoryNewsItemSimple(count: Int = 5) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(count) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier
                        .width(102.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
            }
        }
    }
}
