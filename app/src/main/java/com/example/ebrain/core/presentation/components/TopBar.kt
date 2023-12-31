package com.example.ebrain.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.ebrain.R

@Composable
fun TopBar(
    title: String,
    menuDisc: String,
    addItemDisc: String,
    searchingDisc: String,
    sortingItemsDisc: String,
    onMenuClick:()->Unit,
    onSearchClick:()->Unit,
    onOrderSectionClick:()->Unit,
    onAddItemClick:()->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = menuDisc,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onSearchClick()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = searchingDisc,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
            IconButton(onClick = {
                onOrderSectionClick()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_sort_24),
                    contentDescription = sortingItemsDisc,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
            IconButton(onClick = {
                onAddItemClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = addItemDisc,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}