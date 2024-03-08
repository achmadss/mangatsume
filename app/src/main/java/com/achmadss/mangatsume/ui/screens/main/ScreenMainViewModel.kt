package com.achmadss.mangatsume.ui.screens.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.achmadss.mangatsume.common.BaseViewModel
import com.achmadss.mangatsume.common.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class ScreenMainUIState(
    val currentTabName: String? = null
): Parcelable

@HiltViewModel
class ScreenMainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    override val key: String get() = "ScreenMainUIState"

    val uiState = savedStateHandle.getStateFlow(key, ScreenMainUIState())

    fun handleTabChange(
        tabName: String?
    ) {
        uiState.update(savedStateHandle, key) {
            it.copy(currentTabName = tabName)
        }
    }

}