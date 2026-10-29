package com.valerie.vp_week6.data

import com.valerie.vp_week6.model.Club
import com.valerie.vp_week6.model.Project
import com.valerie.vp_week6.model.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** In-memory single source of truth for clubs and projects. */
class CampusRepository {
    val profile: StudentProfile = CampusData.profile
    val techStackOptions: List<String> = CampusData.techStackOptions

    private val _clubs = MutableStateFlow(CampusData.clubs)
    val clubs: StateFlow<List<Club>> = _clubs.asStateFlow()

    private val _projects = MutableStateFlow(CampusData.projects)
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun toggleJoin(clubId: String) {
        val club = _clubs.value.firstOrNull { it.id == clubId } ?: return
        _clubs.update { list -> list.map { if (it.id == clubId) it.copy(isJoined = !it.isJoined) else it } }
        // Leaving a club removes the projects recorded in it.
        if (club.isJoined) _projects.update { list -> list.filterNot { it.clubId == clubId } }
    }

    fun addProject(project: Project) = _projects.update { it + project }

    fun deleteProject(projectId: String) = _projects.update { list -> list.filterNot { it.id == projectId } }

    fun updateProject(projectId: String, transform: (Project) -> Project) =
        _projects.update { list -> list.map { if (it.id == projectId) transform(it) else it } }
}
