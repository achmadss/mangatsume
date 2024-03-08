package com.achmadss.mangatsume.common

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

fun <T> StateFlow<T>.update(
    savedStateHandle: SavedStateHandle,
    key: String,
    update: (currentValue: T) -> T
) {
    savedStateHandle[key] = update(savedStateHandle.get<T>(key)
        ?: throw IllegalArgumentException("No value found for key $key"))
}