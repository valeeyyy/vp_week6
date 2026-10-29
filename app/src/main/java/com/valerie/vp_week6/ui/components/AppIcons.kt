package com.valerie.vp_week6.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valerie.vp_week6.model.Club
import com.valerie.vp_week6.model.Project

data class IconStyle(val vector: ImageVector, val tint: Color, val tile: Color)

fun iconStyle(key: String): IconStyle = when (key) {
    "code" -> IconStyle(Icons.Default.Code, Color(0xFF2F55D4), Color(0xFFEEF3FF))
    "shield" -> IconStyle(Icons.Default.Security, Color(0xFFD32F2F), Color(0xFFFDE4E4))
    "ai" -> IconStyle(Icons.Default.AutoAwesome, Color(0xFF7C4DDB), Color(0xFFEDE9FE))
    "phone" -> IconStyle(Icons.Default.Smartphone, Color(0xFF1E73BE), Color(0xFFE3F0FF))
    "web" -> IconStyle(Icons.Default.Language, Color(0xFF0F9D8A), Color(0xFFE0F7F4))
    "data" -> IconStyle(Icons.Default.BarChart, Color(0xFFE08A00), Color(0xFFFFF1DD))
    "android" -> IconStyle(Icons.Default.Android, Color(0xFF0D52CE), Color(0xFFEEF3FF))
    "codegreen" -> IconStyle(Icons.Default.Code, Color(0xFF2E9E4F), Color(0xFFE3F7E8))
    "qr" -> IconStyle(Icons.Default.QrCodeScanner, Color(0xFF2E9E4F), Color(0xFFE3F7E8))
    "cloud" -> IconStyle(Icons.Default.Cloud, Color(0xFF6D3FE0), Color(0xFFEDE9FE))
    "groups" -> IconStyle(Icons.Default.People, Color(0xFF1A3AA8), Color(0xFFE0E9FF))
    "layers" -> IconStyle(Icons.Default.FilterNone, Color(0xFF4A22B8), Color(0xFFEDE9FE))
    else -> IconStyle(Icons.Default.Code, Color(0xFF2F55D4), Color(0xFFEEF3FF))
}

@Composable
fun IconTile(key: String, modifier: Modifier = Modifier, size: Dp = 52.dp) {
    val style = iconStyle(key)
    Box(
        modifier = modifier.size(size).background(style.tile, RoundedCornerShape(size * 0.28f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(style.vector, contentDescription = null, tint = style.tint, modifier = Modifier.size(size * when (key) { "layers" -> 0.42f; "android" -> 0.6f; else -> 0.5f }))
    }
}

@Composable
fun ClubIcon(club: Club, modifier: Modifier = Modifier, size: Dp = 52.dp) = IconTile(club.icon, modifier, size)

@Composable
fun ProjectIcon(project: Project, modifier: Modifier = Modifier, size: Dp = 52.dp) = IconTile(project.icon, modifier, size)
