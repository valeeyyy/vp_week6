package com.valerie.vp_week6.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valerie.vp_week6.model.Club
import com.valerie.vp_week6.ui.components.AppCard
import com.valerie.vp_week6.ui.components.ClubIcon
import com.valerie.vp_week6.ui.components.EmptyMessage
import com.valerie.vp_week6.ui.components.JoinedPill
import com.valerie.vp_week6.ui.components.ProjectIcon
import com.valerie.vp_week6.ui.components.StatusBadge
import com.valerie.vp_week6.ui.components.TechTag
import com.valerie.vp_week6.ui.components.androidxClickable
import com.valerie.vp_week6.ui.theme.Blue600
import com.valerie.vp_week6.ui.theme.Blue900
import com.valerie.vp_week6.ui.theme.RedJoined
import com.valerie.vp_week6.ui.theme.RedSoft
import com.valerie.vp_week6.ui.theme.Slate200
import com.valerie.vp_week6.util.Messages
import com.valerie.vp_week6.viewmodel.CampusViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ClubProjectsScreen(
    clubId: String,
    viewModel: CampusViewModel,
    onBack: () -> Unit,
    onProjectClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val showDialog by viewModel.showDialog.collectAsStateWithLifecycle()
    val club = state.clubs.firstOrNull { it.id == clubId }
    val projects = state.projectsOf(clubId)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("${club?.name.orEmpty()} Projects", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
                        Text(club?.fullName.orEmpty(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = viewModel::openDialog,
                modifier = Modifier.shadow(14.dp, RoundedCornerShape(16.dp), ambientColor = Color(0xFFB8BEC8), spotColor = Color(0xFFB8BEC8)),
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                containerColor = Color(0xFFDCE6FF),
                contentColor = Blue900,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Project", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp, end = 20.dp,
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (club != null) {
                item {
                    Row(
                        Modifier.fillMaxWidth()
                            .background(Brush.horizontalGradient(listOf(Color(0xFFE6EEFF), Color(0xFFD6E3FF))), RoundedCornerShape(20.dp))
                            .border(1.dp, Color(0xFFA9C0F5), RoundedCornerShape(20.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(club.fullName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = Blue900)
                            Text("${club.members} Anggota • ${projects.size} Proyek Aktif", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Blue600)
                        }
                        JoinedPill(containerColor = Color.White)
                    }
                }
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.FilterNone, contentDescription = null, tint = Color(0xFF2F55D4), modifier = Modifier.size(22.dp))
                    Text("Daftar Proyek Mahasiswa (${projects.size})", Modifier.padding(start = 8.dp), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold)
                }
            }
            if (projects.isEmpty()) item { EmptyMessage(Messages.NO_PROJECTS) }
            items(projects, key = { it.id }) { project ->
                AppCard(Modifier.fillMaxWidth(), onClick = { onProjectClick(project.id) }) {
                    Column(Modifier.padding(16.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            ProjectIcon(project, size = 46.dp)
                            Column(Modifier.weight(1f)) {
                                Text(project.title, fontSize = 16.sp, lineHeight = 20.sp, fontWeight = FontWeight.ExtraBold)
                                Text(project.description, fontSize = 12.sp, lineHeight = 17.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
                            }
                            Box(
                                Modifier.size(40.dp).background(RedSoft, CircleShape).clickableNoRipple { viewModel.deleteProject(project.id) },
                                contentAlignment = Alignment.Center
                            ) { Text("−", color = RedJoined, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
                        }
                        FlowRow(
                            Modifier.padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) { project.techStack.forEach { TechTag(it) } }
                        HorizontalDivider(Modifier.padding(vertical = 10.dp), color = Slate200)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("ID: ${project.id}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            StatusBadge(project.status)
                        }
                    }
                }
            }
        }
    }

    if (showDialog && club != null) {
        AddProjectDialog(viewModel, state.techStackOptions, club)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddProjectDialog(viewModel: CampusViewModel, techOptions: List<String>, club: Club) {
    val form by viewModel.form.collectAsStateWithLifecycle()

    AlertDialog(
        onDismissRequest = {}, // only closable through "Cancel"
        modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false, usePlatformDefaultWidth = false),
        shape = RoundedCornerShape(28.dp),
        containerColor = Color.White,
        title = {
            Column {
                Text("Add Project", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0B0F1A))
                Text(
                    "Komunitas: ${club.name} (${club.fullName})",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Judul Proyek *", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B0F1A))
                OutlinedTextField(
                    value = form.title,
                    onValueChange = viewModel::onTitleChange,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 15.sp),
                    colors = fieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Deskripsi Proyek (Minimal 3 Baris) *", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B0F1A))
                OutlinedTextField(
                    value = form.description,
                    onValueChange = viewModel::onDescriptionChange,
                    minLines = 3,
                    textStyle = TextStyle(fontSize = 15.sp),
                    colors = fieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Tech Stack (Pilihan Multi-Selection) *",
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp,
                        color = Color(0xFF0B0F1A),
                        fontWeight = FontWeight.Bold
                    )
                    Text("${form.techStack.size} Dipilih", style = MaterialTheme.typography.labelMedium, color = Blue600, fontWeight = FontWeight.Bold)
                }
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    techOptions.forEach { tech ->
                        val selected = tech in form.techStack
                        FilterChip(
                            selected = selected,
                            onClick = { viewModel.onTechToggle(tech) },
                            label = { Text(tech, fontWeight = FontWeight.Medium) },
                            leadingIcon = {
                                if (selected) Icon(Icons.Default.Check, contentDescription = null, Modifier.size(16.dp))
                                else Text("+", color = Color(0xFF475569))
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color(0xFFF1F5F9),
                                labelColor = Color(0xFF475569),
                                iconColor = Color(0xFF475569),
                                selectedContainerColor = Color(0xFFDCE6FF),
                                selectedLabelColor = Blue900,
                                selectedLeadingIconColor = Blue900
                            ),
                            border = FilterChipBorder(selected)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(enabled = form.isValid, onClick = { viewModel.saveProject(club.id) }) { Text("Save", fontWeight = FontWeight.Bold) }
        },
        dismissButton = {
            TextButton(onClick = viewModel::cancelDialog) { Text("Cancel", fontWeight = FontWeight.Bold) }
        }
    )
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color(0xFFF1F5F9),
    unfocusedContainerColor = Color(0xFFF1F5F9),
    unfocusedBorderColor = Color(0xFF94A3B8),
    focusedBorderColor = Blue600,
    focusedTextColor = Color(0xFF0B0F1A),
    unfocusedTextColor = Color(0xFF0B0F1A)
)

@Composable
private fun FilterChipBorder(selected: Boolean) =
    androidx.compose.material3.FilterChipDefaults.filterChipBorder(
        enabled = true,
        selected = selected,
        borderColor = Color(0xFFCBD5E1),
        selectedBorderColor = Blue600
    )

private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier =
    this.then(Modifier.androidxClickable(onClick))
