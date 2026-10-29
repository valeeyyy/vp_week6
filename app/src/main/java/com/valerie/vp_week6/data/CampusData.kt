package com.valerie.vp_week6.data

import com.valerie.vp_week6.model.Club
import com.valerie.vp_week6.model.Project
import com.valerie.vp_week6.model.ProjectStatus
import com.valerie.vp_week6.model.StudentProfile

object CampusData {
    val profile = StudentProfile(
        name = "Aditya Pratama",
        major = "Teknik Informatika",
        batch = "Angkatan 2022",
        nim = "22001045"
    )

    val clubs: List<Club> = listOf(
        Club(
            "gdsc", "GDSC", "Google Developer Student Club", "Google Developer Student Club", 142,
            "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.",
            "Google Developer Student Club", "code", isJoined = true
        ),
        Club(
            "cyber", "CyberSecurity Guild", "CyberSecurity Guild", "Security & Pentesting", 88,
            "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
            "Security & Pentesting", "shield", isJoined = true
        ),
        Club(
            "ai", "AI Society", "AI Society", "Machine Learning & LLM", 115,
            "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.",
            "Machine Learning & LLM", "ai"
        ),
        Club(
            "mobile", "Mobile Dev Community", "Mobile Dev Community", "Native Jetpack Compose & iOS", 95,
            "Wadah belajar pengembangan aplikasi mobile native Android dan iOS dari dasar hingga rilis.",
            "Jetpack Compose & KMP", "phone", isJoined = true
        ),
        Club(
            "web", "Web Dev Club", "Web Dev Club", "Frontend & Backend Web", 76,
            "Membangun aplikasi web modern dengan React, Node.js, dan layanan cloud.",
            "Frontend & Backend Web", "web"
        ),
        Club(
            "data", "Data Science Hub", "Data Science Hub", "Analitik & Big Data", 64,
            "Analisis data, visualisasi, dan pipeline data untuk pengambilan keputusan.",
            "Analitik & Big Data", "data"
        )
    )

    val projects: List<Project> = listOf(
        Project(
            "proj-gdsc-01", "gdsc", "Smart Campus Navigation",
            "Navigasi peta gedung perkuliahan, lab komputer, dan fasilitas kampus real-time.",
            listOf("Kotlin", "Jetpack Compose", "Room", "Google Maps SDK"), icon = "android"
        ),
        Project(
            "proj-gdsc-02", "gdsc", "Student Attendance QR Scanner",
            "Pemindai barcode & QR presensi kehadiran workshop berbasis CameraX & Firebase.",
            listOf("Jetpack Compose", "Firebase", "CameraX"), ProjectStatus.COMPLETED, "codegreen"
        ),
        Project(
            "proj-gdsc-03", "gdsc", "Developer RSVP Portal",
            "Portal pendaftaran webinar dan bootcamp teknologi sinkronisasi Ktor API server.",
            listOf("Kotlin", "Ktor", "Jetpack Compose"), icon = "cloud"
        ),
        Project(
            "proj-cyber-01", "cyber", "Campus Wi-Fi Security Audit",
            "Audit keamanan jaringan Wi-Fi kampus dan laporan celah kerentanan.",
            listOf("Python", "Kotlin"), icon = "shield"
        )
    )

    val techStackOptions: List<String> = listOf(
        "Kotlin", "Jetpack Compose", "Room", "Firebase", "Ktor", "Python", "Google Maps SDK", "CameraX"
    )
}
