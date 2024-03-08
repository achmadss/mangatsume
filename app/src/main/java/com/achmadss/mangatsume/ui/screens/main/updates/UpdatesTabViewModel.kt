package com.achmadss.mangatsume.ui.screens.main.updates

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.achmadss.mangatsume.common.BaseViewModel
import com.achmadss.mangatsume.common.update
import com.achmadss.mangatsume.services.UpdateService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class UpdatesTabUIState(
    val badgeCount: Int = 0,
): Parcelable

@HiltViewModel
class UpdatesTabViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    override val key: String get() = "UpdatesTabUIState"

    val uiState = savedStateHandle.getStateFlow(key, UpdatesTabUIState())

    fun handleRefresh() {

    }

    fun handleUpdateBadge(value: Int) = viewModelScope.launch {
        uiState.update(savedStateHandle, key) {
            it.copy(badgeCount = value)
        }
    }

    fun handleUpdate(
        context: Context,
        onAlreadyUpdating: () -> Unit,
    ) {
        UpdateService.startService(context) { onAlreadyUpdating() }
    }

}