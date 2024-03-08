package com.achmadss.mangatsume.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.achmadss.mangatsume.R

@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onSearch: (query: String) -> Unit,
    actions: @Composable () -> Unit = { },
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearching) {
        if (isSearching) focusRequester.requestFocus()
    }

    BackHandler(enabled = isSearching) {
        if (isSearching) isSearching = false
    }

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if (isSearching) {
                IconButton(onClick = { isSearching = false }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            }
        },
        title = {
            if (!isSearching) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
                return@TopAppBar
            }
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = "Search...",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(searchQuery)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            onSearch(searchQuery)
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        }
                    }
                },
                singleLine = true
            )
        },
        actions = {
            if (!isSearching) {
                IconButton(onClick = { isSearching = true }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
                actions.invoke()
            }
        }
    )
}