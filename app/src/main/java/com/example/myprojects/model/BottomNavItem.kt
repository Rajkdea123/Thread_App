package com.example.myprojects.model

import android.icu.text.CaseMap.Title
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myprojects.navigation.Routes

data class BottomNavItem(
    val title: String,
    val routes: String,
    val icon : ImageVector
)
