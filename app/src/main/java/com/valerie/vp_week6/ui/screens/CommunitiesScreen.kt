package com.valerie.vp_week6.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.unit.sp
import com.valerie.vp_week6.ui.theme.Navy
import com.valerie.vp_week6.ui.theme.Slate500
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valerie.vp_week6.model.ClubFilter
import com.valerie.vp_week6.ui.components.AppCard
import com.valerie.vp_week6.ui.components.ClubIcon
import com.valerie.vp_week6.ui.components.JoinedButton
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.LaunchedEffect
import com.valerie.vp_week6.ui.components.ScreenHeader
import com.valerie.vp_week6.ui.theme.Blue600
import com.valerie.vp_week6.ui.theme.Slate200
import com.valerie.vp_week6.viewmodel.CampusViewModel

@Composable
fun CommunitiesScreen(
    viewModel: CampusViewModel,
    contentPadding: PaddingValues,
    onOpenClub: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    LaunchedEffect(state.filter, state.query) { listState.scrollToItem(0) }

    Column(Modifier.fillMaxSize()) {
        ScreenHeader(
            "Communities Directory",
            "Pilih komunitas untuk membuka Club Projects",
            subtitleFontWeight = FontWeight.Medium
        ) {
            IconButton(onClick = viewModel::toggleSearch) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Navy)
            }
        }
        if (state.searchOpen) {
            OutlinedTextField(
                value = state.query,
                onValueChange = viewModel::onQueryChange,
                placeholder = { Text("Cari komunitas...") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 12.dp)
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ClubFilter.entries) { f ->
                FilterChip(
                    selected = state.filter == f,
                    onClick = { viewModel.setFilter(f) },
                    label = { Text(f.label) },
                    shape = RoundedCornerShape(50)
                )
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 4.dp, bottom = contentPadding.calculateBottomPadding() + 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(state.filteredClubs, key = { it.id }) { club ->
                AppCard(
                    Modifier.fillMaxWidth(),
                    onClick = { if (viewModel.canOpenClub(club.id)) onOpenClub(club.id) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            ClubIcon(club, size = 58.dp)
                            Column {
                                Text(club.name, fontSize = 20.sp, lineHeight = 24.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
                                Text(
                                    "${if (club.id == "gdsc") club.fullName else club.field} • ${club.members} Mahasiswa",
                                    fontSize = 12.sp, lineHeight = 16.sp,
                                    color = Slate500
                                )
                            }
                        }
                        Text(club.description, fontSize = 15.sp, lineHeight = 22.sp, color = Color(0xFF334155), modifier = Modifier.padding(top = 12.dp))
                        HorizontalDivider(Modifier.padding(vertical = 12.dp), color = Slate200)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (club.isJoined) {
                                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Outlined.LockOpen, null, tint = Blue600, modifier = Modifier.size(18.dp))
                                    Text(
                                        "  Unlocked • Buka Proyek ",
                                        fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Blue600
                                    )
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Blue600, modifier = Modifier.size(16.dp))
                                }
                                JoinedButton(onClick = { viewModel.toggleJoin(club.id) })
                            } else {
                                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.Lock, null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
                                    Text(
                                        "  Terkunci (Gate Akses)",
                                        fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF94A3B8)
                                    )
                                }
                                Button(
                                    onClick = { viewModel.toggleJoin(club.id) },
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                                    Text("  Join", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
