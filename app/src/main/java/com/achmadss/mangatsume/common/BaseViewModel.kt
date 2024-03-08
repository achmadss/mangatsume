package com.achmadss.mangatsume.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    abstract val key: String
}