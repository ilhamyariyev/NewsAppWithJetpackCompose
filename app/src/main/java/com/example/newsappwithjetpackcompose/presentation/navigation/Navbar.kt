package com.example.newsappwithjetpackcompose.presentation.navigation

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsappwithjetpackcompose.R

@Composable
fun BottomBar(
    navController: NavController,
    selectedIndex: Int?,
    onItemSelected: (Int) -> Unit,
) {

    val items = listOf(
        R.drawable.home,
        R.drawable.save,
    )

    val selectedItems = listOf(
        R.drawable.home_filled,
        R.drawable.save_filled,
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        windowInsets = WindowInsets.navigationBars
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {

            items.forEachIndexed { index, icon ->

                val selected = selectedIndex == index

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (!selected) {
                                onItemSelected(index)
                                when (index) {
                                    0 -> navController.navigate(Screen.Home) {
                                        popUpTo(Screen.Home) { inclusive = false }
                                        launchSingleTop = true
                                    }

                                    1 -> navController.navigate(Screen.Favorite) {
                                        popUpTo(Screen.Home) { inclusive = false }
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                        .padding(vertical = 6.dp)
                ) {

                    Icon(
                        painter = painterResource(
                            if (selected) selectedItems[index] else items[index]
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}