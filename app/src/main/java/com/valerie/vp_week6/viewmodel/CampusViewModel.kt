package com.valerie.vp_week6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerie.vp_week6.data.CampusRepository
import com.valerie.vp_week6.model.Club
import com.valerie.vp_week6.model.ClubFilter
import com.valerie.vp_week6.model.Project
import com.valerie.vp_week6.model.ProjectForm
import com.valerie.vp_week6.model.ProjectStatus
import com.valerie.vp_week6.model.StudentProfile
import com.valerie.vp_week6.util.Messages
import com.valerie.vp_week6.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CampusUiState(
    val profile: StudentProfile,
    val clubs: List<Club> = emptyList(),
    val projects: List<Project> = emptyList(),
    val techStackOptions: List<String> = emptyList(),
    val filter: ClubFilter = ClubFilter.ALL,
    val searchOpen: Boolean = false,
    val query: String = ""
) {
    val filteredClubs: List<Club>
        get() = byFilter().filter {
            query.isBlank() || it.name.contains(query, true) || it.field.contains(query, true)
        }

    private fun byFilter(): List<Club> =
        when (filter) {
            ClubFilter.ALL -> clubs
            ClubFilter.JOINED -> clubs.filter { it.isJoined }
            ClubFilter.NOT_JOINED -> clubs.filterNot { it.isJoined }
        }

    fun projectsOf(clubId: String) = projects.filter { it.clubId == clubId }

    val joinedClubs: List<Club> get() = clubs.filter { it.isJoined }
    val totalProjects: Int get() = projects.size
}

class CampusViewModel(
    private val repository: CampusRepository = CampusRepository()
) : ViewModel() {

    private val _filter = MutableStateFlow(ClubFilter.ALL)
    private val _search = MutableStateFlow(false to "")

    val uiState: StateFlow<CampusUiState> = combine(
        repository.clubs, repository.projects, _filter, _search
    ) { clubs, projects, filter, search ->
        CampusUiState(
            profile = repository.profile,
            clubs = clubs,
            projects = projects,
            techStackOptions = repository.techStackOptions,
            filter = filter,
            searchOpen = search.first,
            query = search.second
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        CampusUiState(repository.profile, repository.clubs.value, repository.projects.value, repository.techStackOptions)
    )

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _form = MutableStateFlow(ProjectForm())
    val form: StateFlow<ProjectForm> = _form.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 4)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    fun toggleSearch() { _search.update { (open, _) -> !open to "" } }
    fun onQueryChange(q: String) { _search.update { it.first to q } }

    fun setFilter(filter: ClubFilter) { _filter.value = filter }

    fun toggleJoin(clubId: String) = repository.toggleJoin(clubId)

        fun canOpenClub(clubId: String): Boolean {
        val joined = uiState.value.clubs.firstOrNull { it.id == clubId }?.isJoined == true
        if (!joined) _events.tryEmit(UiEvent.ShowMessage(Messages.JOIN_FIRST))
        return joined
    }

    fun deleteProject(projectId: String) = repository.deleteProject(projectId)

    fun setProjectStatus(projectId: String, status: ProjectStatus) =
        repository.updateProject(projectId) { it.copy(status = status) }

    fun openDialog() {
        _form.value = ProjectForm()
        _showDialog.value = true
    }

    fun cancelDialog() {
        _showDialog.value = false
        _form.value = ProjectForm()
    }

    fun onTitleChange(value: String) = _form.update { it.copy(title = value) }
    fun onDescriptionChange(value: String) = _form.update { it.copy(description = value) }
    fun onTechToggle(tech: String) = _form.update {
        it.copy(techStack = if (tech in it.techStack) it.techStack - tech else it.techStack + tech)
    }

    fun saveProject(clubId: String) {
        val f = _form.value
        if (!f.isValid) return
        repository.addProject(
            Project(
                id = nextProjectId(clubId),
                clubId = clubId,
                title = f.title.trim(),
                description = f.description.trim(),
                techStack = uiState.value.techStackOptions.filter { it in f.techStack },
                icon = uiState.value.clubs.firstOrNull { it.id == clubId }?.icon ?: "code"
            )
        )
        cancelDialog()
    }

    private fun nextProjectId(clubId: String): String {
        val used = uiState.value.projects.map { it.id }.toSet()
        var n = uiState.value.projectsOf(clubId).size + 1
        while ("proj-$clubId-%02d".format(n) in used) n++
        return "proj-$clubId-%02d".format(n)
    }
}
