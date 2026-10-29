package com.valerie.vp_week6.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valerie.vp_week6.model.Club
import com.valerie.vp_week6.model.ProjectStatus
import com.valerie.vp_week6.ui.theme.AmberBg
import com.valerie.vp_week6.ui.theme.AmberFg
import com.valerie.vp_week6.ui.theme.Blue600
import com.valerie.vp_week6.ui.theme.GreenBg
import com.valerie.vp_week6.ui.theme.GreenFg
import com.valerie.vp_week6.ui.theme.Navy
import com.valerie.vp_week6.ui.theme.RedJoined
import com.valerie.vp_week6.ui.theme.RedSoft
import com.valerie.vp_week6.ui.theme.Slate100
import com.valerie.vp_week6.ui.theme.Slate200

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val colors = CardDefaults.cardColors(containerColor = Color.White)
    val shape = RoundedCornerShape(20.dp)
    val border = BorderStroke(1.dp, Slate200)
    if (onClick != null) {
        Card(onClick = onClick, modifier = modifier, shape = shape, colors = colors, border = border) { content() }
    } else {
        Card(modifier = modifier, shape = shape, colors = colors, border = border) { content() }
    }
}

@Composable
fun ScreenHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    subtitleFontWeight: FontWeight = FontWeight.Normal,
    trailing: @Composable () -> Unit = {}
) {
    Surface(color = Color.White, modifier = modifier.fillMaxWidth()) {
        Column(Modifier.statusBarsPadding()) {
            Row(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(title, fontSize = 22.sp, lineHeight = 26.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
                    Text(
                        subtitle,
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontWeight = subtitleFontWeight,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                trailing()
            }
            Box(Modifier.fillMaxWidth().size(1.dp).background(Slate200))
        }
    }
}

@Composable
fun SectionHeader(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    trailing: String,
    modifier: Modifier = Modifier,
    iconVerticalOffset: Dp = 0.dp
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.offset(y = iconVerticalOffset).size(22.dp)
        )
        Text(
            title,
            modifier = Modifier.weight(1f).padding(start = 5.dp),
            fontSize = 16.sp,
            lineHeight = 22.sp,
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeightStyle = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.Both)
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.ExtraBold,
            color = Navy
        )
        Text(trailing, fontSize = 13.sp, maxLines = 1, color = Blue600, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EmptyMessage(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

val BoldCheck: ImageVector = ImageVector.Builder("BoldCheck", 24.dp, 24.dp, 24f, 24f).apply {
    path(stroke = SolidColor(Color.Black), strokeLineWidth = 4.6f, strokeLineCap = StrokeCap.Round) {
        moveTo(4.5f, 12.5f); lineTo(9.5f, 17.5f); lineTo(19.5f, 6.5f)
    }
}.build()

val JoinedBorder = Color(0xFFEBC4C4)

@Composable
fun JoinedPill(modifier: Modifier = Modifier, containerColor: Color = RedSoft, onClick: (() -> Unit)? = null) {
    Surface(
        modifier = modifier.then(if (onClick == null) Modifier else Modifier.clickable(onClick = onClick)),
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        border = BorderStroke(1.dp, JoinedBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text("Joined", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RedJoined)
            Icon(BoldCheck, contentDescription = null, tint = RedJoined, modifier = Modifier.size(12.dp))
        }
    }
}

@Composable
fun JoinedButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = RedSoft,
        border = BorderStroke(1.5.dp, RedJoined)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(BoldCheck, contentDescription = null, tint = RedJoined, modifier = Modifier.size(14.dp))
            Text("Joined", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RedJoined)
        }
    }
}

@Composable
fun TechTag(text: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(8.dp), color = Slate100) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StatusBadge(status: ProjectStatus, modifier: Modifier = Modifier) {
    val done = status == ProjectStatus.COMPLETED
    val fg = if (done) GreenFg else AmberFg
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = if (done) GreenBg else AmberBg) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(Modifier.size(6.dp).background(fg, CircleShape))
            Text(status.label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = fg)
        }
    }
}

fun Modifier.androidxClickable(onClick: () -> Unit): Modifier =
    this.clickable(onClick = onClick)
