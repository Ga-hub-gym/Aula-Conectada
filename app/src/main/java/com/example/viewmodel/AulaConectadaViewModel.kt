package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class AulaConectadaUiState(
  val currentTab: AppNavTab = AppNavTab.HOME,
  val userProfile: UserProfile = UserProfile(),
  val announcements: List<Announcement> = SampleData.announcements,
  val selectedAnnouncement: Announcement? = null,

  // Safe Space
  val protocolSteps: List<ProtocolStep> = SampleData.protocolSteps,
  val esiTopics: List<EsiTopic> = SampleData.esiTopics,
  val safeSpaceFaqs: List<SafeSpaceFaq> = SampleData.safeSpaceFaqs,
  val safeSpaceSearchQuery: String = "",
  val selectedSafeSpaceTag: String? = null,
  val isConfidentialReportDialogOpen: Boolean = false,
  val submittedReports: List<ConfidentialReport> = emptyList(),

  // Digital Library
  val resources: List<StudyResource> = SampleData.initialResources,
  val librarySearchQuery: String = "",
  val selectedGradeYearFilter: String = "Todos",
  val selectedSubjectFilter: String = "Todas",
  val isUploadDialogOpen: Boolean = false,
  val downloadingResourceId: String? = null,

  // Teacher Directory
  val teachers: List<Teacher> = SampleData.teachers,
  val teacherSearchQuery: String = "",
  val selectedTeacher: Teacher? = null,

  // School FAQ & Contact
  val schoolFaqs: List<SchoolFaqItem> = SampleData.schoolFaqs,
  val selectedSchoolFaqCategory: String = "Todos",
  val schoolFaqSearchQuery: String = "",
  val institutionalContacts: List<InstitutionalContact> = SampleData.institutionalContacts,

  // Profile Dialog
  val isProfileDialogOpen: Boolean = false,

  // Toast / Feedback message
  val userFeedbackMessage: String? = null
)

class AulaConectadaViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(AulaConectadaUiState())
  val uiState: StateFlow<AulaConectadaUiState> = _uiState.asStateFlow()

  fun selectTab(tab: AppNavTab) {
    _uiState.update { it.copy(currentTab = tab) }
  }

  fun openConfidentialReportDialog() {
    _uiState.update { it.copy(isConfidentialReportDialogOpen = true) }
  }

  fun closeConfidentialReportDialog() {
    _uiState.update { it.copy(isConfidentialReportDialogOpen = false) }
  }

  fun submitConfidentialReport(
    situationType: String,
    gradeCourse: String,
    isAnonymous: Boolean,
    contactInfo: String,
    urgencyLevel: String,
    message: String
  ) {
    val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
    val report = ConfidentialReport(
      id = "rep-${System.currentTimeMillis()}",
      dateFormatted = dateStr,
      situationType = situationType,
      gradeCourse = gradeCourse,
      isAnonymous = isAnonymous,
      contactInfo = if (isAnonymous) "Anónimo" else contactInfo,
      urgencyLevel = urgencyLevel,
      message = message
    )

    _uiState.update {
      it.copy(
        submittedReports = listOf(report) + it.submittedReports,
        isConfidentialReportDialogOpen = false,
        userFeedbackMessage = "Reporte confidencial enviado con éxito al Gabinete de Orientación."
      )
    }
  }

  fun setSafeSpaceSearchQuery(query: String) {
    _uiState.update { it.copy(safeSpaceSearchQuery = query) }
  }

  fun selectSafeSpaceTag(tag: String?) {
    _uiState.update { current ->
      val newTag = if (current.selectedSafeSpaceTag == tag) null else tag
      current.copy(selectedSafeSpaceTag = newTag)
    }
  }

  // Library actions
  fun setLibrarySearchQuery(query: String) {
    _uiState.update { it.copy(librarySearchQuery = query) }
  }

  fun setLibraryGradeYearFilter(gradeYear: String) {
    _uiState.update { it.copy(selectedGradeYearFilter = gradeYear) }
  }

  fun setLibrarySubjectFilter(subject: String) {
    _uiState.update { it.copy(selectedSubjectFilter = subject) }
  }

  fun openUploadDialog() {
    _uiState.update { it.copy(isUploadDialogOpen = true) }
  }

  fun closeUploadDialog() {
    _uiState.update { it.copy(isUploadDialogOpen = false) }
  }

  fun uploadResource(
    title: String,
    subject: String,
    gradeYear: String,
    resourceType: String,
    description: String,
    simulatedFile: String
  ) {
    val extension = simulatedFile.substringAfterLast('.', "PDF").uppercase()
    val newResource = StudyResource(
      id = "res-${System.currentTimeMillis()}",
      title = title,
      subject = subject,
      gradeYear = gradeYear,
      resourceType = resourceType,
      description = description,
      contributor = "${_uiState.value.userProfile.name} (${_uiState.value.userProfile.gradeYear})",
      uploadDate = "Hoy",
      fileType = if (extension in listOf("PDF", "DOCX", "SLIDES")) extension else "PDF",
      fileSize = "2.5 MB",
      rating = 5.0f,
      ratingCount = 1,
      isFavorite = true
    )

    _uiState.update {
      it.copy(
        resources = listOf(newResource) + it.resources,
        isUploadDialogOpen = false,
        userFeedbackMessage = "¡Tu apunte '$title' fue publicado en la biblioteca comunitaria!"
      )
    }
  }

  fun toggleFavoriteResource(resourceId: String) {
    _uiState.update { current ->
      val updatedList = current.resources.map { res ->
        if (res.id == resourceId) res.copy(isFavorite = !res.isFavorite) else res
      }
      val res = updatedList.firstOrNull { it.id == resourceId }
      val msg = if (res?.isFavorite == true) "Guardado en tus favoritos" else "Eliminado de favoritos"
      current.copy(resources = updatedList, userFeedbackMessage = msg)
    }
  }

  fun rateResource(resourceId: String, ratingScore: Int) {
    _uiState.update { current ->
      val updatedList = current.resources.map { res ->
        if (res.id == resourceId) {
          val newCount = res.ratingCount + 1
          val newRating = ((res.rating * res.ratingCount) + ratingScore) / newCount
          res.copy(
            rating = String.format(Locale.US, "%.1f", newRating).toFloat(),
            ratingCount = newCount
          )
        } else {
          res
        }
      }
      current.copy(
        resources = updatedList,
        userFeedbackMessage = "¡Gracias por calificar con $ratingScore estrellas!"
      )
    }
  }

  fun downloadResource(resource: StudyResource) {
    viewModelScope.launch {
      _uiState.update { it.copy(downloadingResourceId = resource.id) }
      kotlinx.coroutines.delay(1200) // gentle visual download feedback
      _uiState.update {
        it.copy(
          downloadingResourceId = null,
          userFeedbackMessage = "Descarga completa: '${resource.title}.${resource.fileType.lowercase()}' guardado."
        )
      }
    }
  }

  // Teacher Directory actions
  fun setTeacherSearchQuery(query: String) {
    _uiState.update { it.copy(teacherSearchQuery = query) }
  }

  fun selectTeacher(teacher: Teacher?) {
    _uiState.update { it.copy(selectedTeacher = teacher) }
  }

  // School FAQ & Contact actions
  fun setSchoolFaqCategory(category: String) {
    _uiState.update { it.copy(selectedSchoolFaqCategory = category) }
  }

  fun setSchoolFaqSearchQuery(query: String) {
    _uiState.update { it.copy(schoolFaqSearchQuery = query) }
  }

  // Announcements
  fun selectAnnouncement(announcement: Announcement?) {
    _uiState.update { it.copy(selectedAnnouncement = announcement) }
  }

  // Profile Dialog
  fun setProfileDialogOpen(open: Boolean) {
    _uiState.update { it.copy(isProfileDialogOpen = open) }
  }

  fun clearFeedbackMessage() {
    _uiState.update { it.copy(userFeedbackMessage = null) }
  }
}
