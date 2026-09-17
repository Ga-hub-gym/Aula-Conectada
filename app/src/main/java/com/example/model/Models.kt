package com.example.model

enum class AppNavTab(val label: String) {
  HOME("Inicio"),
  SAFE_SPACE("Espacio Seguro"),
  LIBRARY("Biblioteca"),
  TEACHERS("Profesores"),
  FAQ_CONTACT("Preguntas & Contacto")
}

data class Announcement(
  val id: String,
  val title: String,
  val date: String,
  val category: String,
  val summary: String,
  val fullDetails: String,
  val location: String,
  val organizer: String,
  val isImportant: Boolean = false
)

data class ProtocolStep(
  val stepNumber: Int,
  val title: String,
  val subtitle: String,
  val description: String,
  val keyActions: List<String>,
  val warningNote: String? = null
)

data class EsiTopic(
  val id: String,
  val title: String,
  val subtitle: String,
  val category: String,
  val summary: String,
  val keyPoints: List<String>,
  val legalReference: String
)

data class SafeSpaceFaq(
  val id: String,
  val question: String,
  val answer: String,
  val tags: List<String>
)

data class ConfidentialReport(
  val id: String,
  val dateFormatted: String,
  val situationType: String,
  val gradeCourse: String,
  val isAnonymous: Boolean,
  val contactInfo: String,
  val urgencyLevel: String, // "Baja", "Media", "Urgente"
  val message: String
)

data class StudyResource(
  val id: String,
  val title: String,
  val subject: String,
  val gradeYear: String, // "1° Año", "2° Año", etc.
  val resourceType: String, // "Resumen", "Modelo de Examen", "Guía", "Apuntes"
  val description: String,
  val contributor: String,
  val uploadDate: String,
  val fileType: String, // "PDF", "DOCX", "SLIDES"
  val fileSize: String,
  val rating: Float,
  val ratingCount: Int,
  val isFavorite: Boolean = false
)

data class Teacher(
  val id: String,
  val name: String,
  val title: String,
  val subjects: List<String>,
  val courses: String,
  val email: String,
  val consultationHours: String,
  val classroom: String,
  val evaluationCriteria: List<String>,
  val assignmentRules: List<String>,
  val recentUpdate: String
)

data class SchoolFaqItem(
  val id: String,
  val category: String,
  val question: String,
  val answer: String
)

data class InstitutionalContact(
  val id: String,
  val department: String,
  val role: String,
  val personInCharge: String,
  val phone: String,
  val email: String,
  val office: String,
  val schedule: String
)

data class UserProfile(
  val name: String = "Camila Torres",
  val role: String = "Estudiante Secundaria",
  val gradeYear: String = "4° Año",
  val division: String = "División B",
  val schoolName: String = "Escuela Normal Superior N° 1",
  val studentId: String = "ENS-2026-4089"
)
