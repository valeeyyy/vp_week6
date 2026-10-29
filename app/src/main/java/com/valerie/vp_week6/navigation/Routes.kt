package com.valerie.vp_week6.navigation

object Routes {
    const val ARG_CLUB_ID = "clubId"
    const val ARG_PROJECT_ID = "projectId"

    const val DASHBOARD = "dashboard"
    const val COMMUNITIES = "communities"
    const val CLUB_PROJECTS = "club_projects/{$ARG_CLUB_ID}"
    const val PROJECT_DETAIL = "project_detail/{$ARG_PROJECT_ID}"

    fun clubProjects(clubId: String) = "club_projects/$clubId"
    fun projectDetail(projectId: String) = "project_detail/$projectId"

    val topLevel = setOf(DASHBOARD, COMMUNITIES)
}
