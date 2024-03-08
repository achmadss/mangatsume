package com.achmadss.mangatsume.ui.screens.main.library

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.achmadss.mangatsume.common.BaseViewModel
import com.achmadss.mangatsume.common.UIState
import com.achmadss.mangatsume.common.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@Parcelize
data class LibraryTabUIState(
    val state: UIState = UIState.OnLoading,
): Parcelable

@HiltViewModel
class LibraryTabViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    override val key: String get() = "LibraryTabUIState"

    val uiState = savedStateHandle.getStateFlow(key, LibraryTabUIState())

    init {
        handleFetchMangaFromLibrary()
    }

    fun handleSearch(query: String) {

    }

    fun handleUpdateLibrary() {

    }

    private fun handleFetchMangaFromLibrary() = viewModelScope.launch {

    }

}