package com.valerie.vp_week6.model

enum class ProjectStatus(val label: String) {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed")
}

enum class ClubFilter(val label: String) {
    ALL("Semua"),
    JOINED("Joined"),
    NOT_JOINED("Belum Join")
}

data class Club(
    val id: String,
    val name: String,
    val fullName: String,
    val field: String,
    val members: Int,
    val description: String,
    val tagline: String,
    val icon: String,
    val isJoined: Boolean = false
)

data class Project(
    val id: String,
    val clubId: String,
    val title: String,
    val description: String,
    val techStack: List<String>,
    val status: ProjectStatus = ProjectStatus.IN_PROGRESS,
    val icon: String = "code"
)

data class StudentProfile(
    val name: String,
    val major: String,
    val batch: String,
    val nim: String
) {
    val initials: String
        get() = name.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString("")
}

data class ProjectForm(
    val title: String = "",
    val description: String = "",
    val techStack: Set<String> = emptySet()
) {
    val isValid: Boolean
        get() = title.isNotBlank() && description.isNotBlank() && techStack.isNotEmpty()
}
