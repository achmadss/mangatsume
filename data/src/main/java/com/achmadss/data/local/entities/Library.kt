package com.achmadss.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Library(
    @PrimaryKey(autoGenerate = true) val id: Int,
)
