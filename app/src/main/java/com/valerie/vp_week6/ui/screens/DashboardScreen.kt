package com.valerie.vp_week6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valerie.vp_week6.ui.components.AppCard
import com.valerie.vp_week6.ui.components.ClubIcon
import com.valerie.vp_week6.ui.components.EmptyMessage
import com.valerie.vp_week6.ui.components.IconTile
import com.valerie.vp_week6.ui.components.JoinedPill
import com.valerie.vp_week6.ui.components.ProjectIcon
import com.valerie.vp_week6.ui.theme.Navy
import com.valerie.vp_week6.ui.components.ScreenHeader
import com.valerie.vp_week6.ui.components.SectionHeader
import com.valerie.vp_week6.ui.components.StatusBadge
import com.valerie.vp_week6.ui.theme.Blue600
import com.valerie.vp_week6.ui.theme.Blue900
import com.valerie.vp_week6.ui.theme.Slate200
import com.valerie.vp_week6.util.Messages
import com.valerie.vp_week6.viewmodel.CampusViewModel

@Composable
fun DashboardScreen(
    viewModel: CampusViewModel,
    contentPadding: PaddingValues,
    onProjectClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val profile = state.profile
    val listState = rememberLazyListState()

    Column(Modifier.fillMaxSize()) {
        ScreenHeader(
            "CampusTech Hub",
            "Student Profile & Portfolio Dashboard",
            subtitleFontWeight = FontWeight.Medium
        ) {
            Box(Modifier.size(36.dp).background(Brush.linearGradient(listOf(Color(0xFF2F55D4), Color(0xFF6D3FE0))), CircleShape), contentAlignment = Alignment.Center) {
                Text(profile.initials, color = Color.White, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 16.dp, bottom = contentPadding.calculateBottomPadding() + 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(
                    Modifier.fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(28.dp), ambientColor = Color(0xFF2F55D4), spotColor = Color(0xFF2F55D4))
                        .background(Brush.linearGradient(listOf(Color(0xFF1E3A9A), Color(0xFF2F55D4), Color(0xFF4D84F2))), RoundedCornerShape(28.dp))
                        .padding(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(Modifier.size(72.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
                            Text(profile.initials, color = Blue900, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Surface(shape = RoundedCornerShape(50), color = Color.White.copy(alpha = 0.16f)) {
                                Text(
                                    "MAHASISWA AKTIF",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF1F5F9)
                                )
                            }
                            Text(profile.name, fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            Text("${profile.major} • ${profile.batch}", style = MaterialTheme.typography.bodySmall, color = Color(0xFFC4CCE0))
                            Text("NIM: ${profile.nim}", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("groups", state.joinedClubs.size, "Joined Clubs", "Komunitas yang Diikuti", Modifier.weight(1f))
                    StatCard("layers", state.totalProjects, "Total Projects", "Seluruh Proyek Dibuat", Modifier.weight(1f))
                }
            }
            item {
                SectionHeader(
                    Icons.Filled.Star,
                    Blue600,
                    "Semua Komunitas yang Diikuti (${state.joinedClubs.size})",
                    "Status: Joined",
                    iconVerticalOffset = (-2).dp
                )
            }
            if (state.joinedClubs.isEmpty()) {
                item { EmptyMessage(Messages.NO_CLUBS) }
            } else {
                items(state.joinedClubs, key = { "club_${it.id}" }) { club ->
                    AppCard(Modifier.fillMaxWidth()) {
                        Row(
                            Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ClubIcon(club, size = 52.dp)
                            Column(Modifier.weight(1f)) {
                                Text(club.name, fontSize = 18.sp, lineHeight = 22.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        club.tagline,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        " • ${state.projectsOf(club.id).size} Proyek",
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp,
                                        maxLines = 1,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            JoinedPill()
                        }
                    }
                }
            }
            item { SectionHeader(Icons.Filled.CropSquare, Color(0xFF6D3FE0), "Semua Proyek Mahasiswa (${state.totalProjects})", "Total: ${state.totalProjects} Proyek") }
            if (state.projects.isEmpty()) {
                item { EmptyMessage(Messages.NO_PROJECTS) }
            } else {
                items(state.projects, key = { "project_${it.id}" }) { project ->
                    val club = state.clubs.firstOrNull { it.id == project.clubId }
                    AppCard(Modifier.fillMaxWidth(), onClick = { onProjectClick(project.id) }) {
                        Column(Modifier.padding(16.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                ProjectIcon(project, size = 52.dp)
                                Column {
                                    Text(
                                        club?.name.orEmpty(),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Blue600
                                    )
                                    Text(project.title, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
                                    Text(
                                        project.techStack.joinToString(" • "),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            HorizontalDivider(Modifier.padding(vertical = 10.dp), color = Slate200)
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                StatusBadge(project.status)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(icon: String, value: Int, title: String, caption: String, modifier: Modifier) {
    AppCard(modifier) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            IconTile(icon, size = 44.dp)
            Text(value.toString(), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(caption, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
