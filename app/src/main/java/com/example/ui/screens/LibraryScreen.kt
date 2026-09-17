package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.StudyResource
import com.example.ui.components.CleanWhiteCard
import com.example.ui.components.RatingDialog
import com.example.ui.components.StarRatingBar
import com.example.ui.theme.*
import com.example.viewmodel.AulaConectadaUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
  uiState: AulaConectadaUiState,
  onSearchChange: (String) -> Unit,
  onYearFilterSelect: (String) -> Unit,
  onSubjectFilterSelect: (String) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onRateResource: (String, Int) -> Unit,
  onDownloadResource: (StudyResource) -> Unit,
  onOpenUploadDialog: () -> Unit,
  onCloseUploadDialog: () -> Unit,
  onUploadSubmit: (title: String, subject: String, gradeYear: String, type: String, desc: String, file: String) -> Unit,
  modifier: Modifier = Modifier
) {
  val years = listOf("Todos", "1° Año", "2° Año", "3° Año", "4° Año", "5° Año")
  val subjects = listOf("Todas", "Matemática", "Historia", "Filosofía", "Inglés", "Biología", "Lengua y Literatura", "Física")

  var resourceToRate by remember { mutableStateOf<StudyResource?>(null) }

  val filteredResources = remember(
    uiState.resources,
    uiState.librarySearchQuery,
    uiState.selectedGradeYearFilter,
    uiState.selectedSubjectFilter
  ) {
    uiState.resources.filter { res ->
      val matchesSearch = uiState.librarySearchQuery.isBlank() ||
        res.title.contains(uiState.librarySearchQuery, ignoreCase = true) ||
        res.description.contains(uiState.librarySearchQuery, ignoreCase = true) ||
        res.contributor.contains(uiState.librarySearchQuery, ignoreCase = true)

      val matchesYear = uiState.selectedGradeYearFilter == "Todos" || res.gradeYear == uiState.selectedGradeYearFilter
      val matchesSubject = uiState.selectedSubjectFilter == "Todas" || res.subject == uiState.selectedSubjectFilter

      matchesSearch && matchesYear && matchesSubject
    }
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = onOpenUploadDialog,
        containerColor = SkyPrimary,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.testTag("upload_resource_fab")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = "Subir Apunte")
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Subir Apunte", fontWeight = FontWeight.Bold)
        }
      }
    },
    modifier = modifier
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(SchoolBackground)
        .padding(innerPadding),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Library Header
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Biblioteca de Apuntes",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            )
            Text(
              text = "Material colaborativo y modelos de examen por año y materia",
              style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
            )
          }
        }
      }

      // Search Bar
      item {
        OutlinedTextField(
          value = uiState.librarySearchQuery,
          onValueChange = onSearchChange,
          placeholder = { Text("Buscar por tema, materia o autor...", color = TextMuted) },
          leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = SkyPrimary)
          },
          trailingIcon = {
            if (uiState.librarySearchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchChange("") }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar", tint = TextMuted)
              }
            }
          },
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = WhiteContainer,
            unfocusedContainerColor = WhiteContainer,
            focusedBorderColor = SkyPrimary,
            unfocusedBorderColor = BorderLight
          ),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("library_search_input")
        )
      }

      // Year Filter Chips
      item {
        Column {
          Text(
            text = "Filtrar por Año / Curso:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 6.dp)
          )
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            items(years) { year ->
              FilterChip(
                selected = uiState.selectedGradeYearFilter == year,
                onClick = { onYearFilterSelect(year) },
                label = { Text(year) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SkyPrimary,
                  selectedLabelColor = Color.White
                ),
                modifier = Modifier.testTag("filter_year_$year")
              )
            }
          }
        }
      }

      // Subject Filter Chips
      item {
        Column {
          Text(
            text = "Filtrar por Materia:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            ),
            modifier = Modifier.padding(bottom = 6.dp)
          )
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            items(subjects) { subject ->
              FilterChip(
                selected = uiState.selectedSubjectFilter == subject,
                onClick = { onSubjectFilterSelect(subject) },
                label = { Text(subject) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = CyanAccent,
                  selectedLabelColor = Color.White
                ),
                modifier = Modifier.testTag("filter_subject_$subject")
              )
            }
          }
        }
      }

      // Results count & indicator
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${filteredResources.size} recursos encontrados",
            style = MaterialTheme.typography.labelMedium.copy(
              color = TextMuted,
              fontWeight = FontWeight.SemiBold
            )
          )
          if (uiState.selectedGradeYearFilter != "Todos" || uiState.selectedSubjectFilter != "Todas") {
            TextButton(
              onClick = {
                onYearFilterSelect("Todos")
                onSubjectFilterSelect("Todas")
              }
            ) {
              Text("Restablecer filtros", color = SkyPrimary, fontSize = 12.sp)
            }
          }
        }
      }

      // Empty State
      if (filteredResources.isEmpty()) {
        item {
          CleanWhiteCard {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Icon(
                imageVector = Icons.Default.FolderOpen,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "No se encontraron materiales para estos filtros.",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = TextPrimary
                )
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "¿Tenés apuntes de este tema? ¡Sé el primero en compartirlos con el botón 'Subir Apunte'!",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextMuted,
                  lineHeight = 18.sp
                )
              )
            }
          }
        }
      } else {
        items(filteredResources, key = { it.id }) { resource ->
          StudyResourceCard(
            resource = resource,
            isDownloading = uiState.downloadingResourceId == resource.id,
            onFavoriteClick = { onToggleFavorite(resource.id) },
            onRateClick = { resourceToRate = resource },
            onDownloadClick = { onDownloadResource(resource) }
          )
        }
      }

      // Bottom padding so content is not hidden by FAB
      item {
        Spacer(modifier = Modifier.height(64.dp))
      }
    }
  }

  // Rate dialog
  resourceToRate?.let { res ->
    RatingDialog(
      currentRating = res.rating,
      onDismiss = { resourceToRate = null },
      onRateSubmit = { stars ->
        onRateResource(res.id, stars)
        resourceToRate = null
      }
    )
  }

  // Upload Resource Dialog
  if (uiState.isUploadDialogOpen) {
    UploadResourceDialog(
      onDismiss = onCloseUploadDialog,
      onUploadSubmit = onUploadSubmit
    )
  }
}

@Composable
private fun StudyResourceCard(
  resource: StudyResource,
  isDownloading: Boolean,
  onFavoriteClick: () -> Unit,
  onRateClick: () -> Unit,
  onDownloadClick: () -> Unit
) {
  CleanWhiteCard(modifier = Modifier.testTag("resource_card_${resource.id}")) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.Top
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
        // File type badge icon
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = when (resource.fileType) {
            "PDF" -> AlertCrimsonContainer
            "DOCX" -> SkyContainer
            else -> WarningAmberContainer
          },
          modifier = Modifier.size(44.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = when (resource.fileType) {
                  "PDF" -> Icons.Default.PictureAsPdf
                  "DOCX" -> Icons.Default.Description
                  else -> Icons.Default.Slideshow
                },
                contentDescription = resource.fileType,
                tint = when (resource.fileType) {
                  "PDF" -> AlertCrimson
                  "DOCX" -> SkyPrimary
                  else -> WarningAmber
                },
                modifier = Modifier.size(20.dp)
              )
              Text(
                text = resource.fileType,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 9.sp,
                  color = when (resource.fileType) {
                    "PDF" -> AlertCrimson
                    "DOCX" -> SkyPrimary
                    else -> WarningAmber
                  }
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = resource.title,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary,
              fontSize = 15.sp
            )
          )
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 2.dp)
          ) {
            Text(
              text = "${resource.subject} • ${resource.gradeYear}",
              style = MaterialTheme.typography.bodySmall.copy(
                color = SkyPrimaryDark,
                fontWeight = FontWeight.Medium
              )
            )
          }
        }
      }

      IconButton(
        onClick = onFavoriteClick,
        modifier = Modifier.size(36.dp).testTag("favorite_btn_${resource.id}")
      ) {
        Icon(
          imageVector = if (resource.isFavorite) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
          contentDescription = "Favorito",
          tint = if (resource.isFavorite) SkyPrimary else TextMuted
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = resource.description,
      style = MaterialTheme.typography.bodySmall.copy(
        color = TextSecondary,
        lineHeight = 18.sp
      )
    )

    Spacer(modifier = Modifier.height(12.dp))

    HorizontalDivider(color = BorderLight)

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        StarRatingBar(
          rating = resource.rating,
          ratingCount = resource.ratingCount,
          onRateClick = onRateClick,
          modifier = Modifier.testTag("rate_bar_${resource.id}")
        )
        Text(
          text = "Por ${resource.contributor} • ${resource.fileSize}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextMuted,
            fontSize = 11.sp
          )
        )
      }

      FilledTonalButton(
        onClick = onDownloadClick,
        colors = ButtonDefaults.filledTonalButtonColors(
          containerColor = SkyContainer,
          contentColor = SkyPrimaryDark
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = !isDownloading,
        modifier = Modifier.testTag("download_resource_btn_${resource.id}")
      ) {
        if (isDownloading) {
          CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp,
            color = SkyPrimary
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text("Descargando...", fontSize = 12.sp)
        } else {
          Icon(
            imageVector = Icons.Default.Download,
            contentDescription = "Descargar",
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text("Abrir", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadResourceDialog(
  onDismiss: () -> Unit,
  onUploadSubmit: (title: String, subject: String, gradeYear: String, type: String, desc: String, file: String) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var subject by remember { mutableStateOf("Matemática") }
  var gradeYear by remember { mutableStateOf("4° Año") }
  var resourceType by remember { mutableStateOf("Resumen") }
  var description by remember { mutableStateOf("") }
  var simulatedFile by remember { mutableStateOf("apuntes_matematica_funciones.pdf") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  val subjectsList = listOf("Matemática", "Historia", "Filosofía", "Inglés", "Biología", "Lengua y Literatura", "Física")
  val yearsList = listOf("1° Año", "2° Año", "3° Año", "4° Año", "5° Año")
  val typesList = listOf("Resumen", "Modelo de Examen", "Guía de Ejercicios", "Apuntes")

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(24.dp),
    containerColor = WhiteContainer,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(SkyContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CloudUpload,
            contentDescription = null,
            tint = SkyPrimary,
            modifier = Modifier.size(22.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Subir Recurso de Estudio",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Text(
            text = "Compartí tu material con la comunidad estudiantil",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
          )
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = title,
          onValueChange = {
            title = it
            errorMessage = null
          },
          label = { Text("Título del material") },
          placeholder = { Text("Ej: Resumen Funciones Trigonométricas") },
          shape = RoundedCornerShape(12.dp),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("upload_title_input")
        )

        // Subject selector
        Column {
          Text(
            text = "Materia:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(subjectsList) { subj ->
              FilterChip(
                selected = subject == subj,
                onClick = { subject = subj },
                label = { Text(subj, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SkyPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }

        // Year selector
        Column {
          Text(
            text = "Año / Curso:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            yearsList.forEach { yr ->
              FilterChip(
                selected = gradeYear == yr,
                onClick = { gradeYear = yr },
                label = { Text(yr, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SkyPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }

        // Type selector
        Column {
          Text(
            text = "Tipo de recurso:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            typesList.forEach { tp ->
              FilterChip(
                selected = resourceType == tp,
                onClick = { resourceType = tp },
                label = { Text(tp, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = CyanAccent,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }

        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Breve descripción del contenido") },
          placeholder = { Text("Fórmulas clave, ejercicios resueltos, temas incluidos...") },
          minLines = 2,
          maxLines = 4,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("upload_desc_input")
        )

        // Simulated file attachment selector
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = SchoolBackground,
          border = BorderStroke(1.dp, BorderMedium),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = null,
                tint = SkyPrimary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Archivo Adjunto:",
                  style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 10.sp)
                )
                Text(
                  text = simulatedFile,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                  )
                )
              }
            }
            TextButton(
              onClick = {
                simulatedFile = when (simulatedFile) {
                  "apuntes_matematica_funciones.pdf" -> "modelo_parcial_historia.docx"
                  "modelo_parcial_historia.docx" -> "guia_ejercicios_fisica.pdf"
                  else -> "apuntes_matematica_funciones.pdf"
                }
              }
            ) {
              Text("Cambiar", color = SkyPrimary, fontSize = 11.sp)
            }
          }
        }

        errorMessage?.let { err ->
          Text(
            text = err,
            style = MaterialTheme.typography.bodySmall.copy(
              color = AlertCrimson,
              fontWeight = FontWeight.Medium
            )
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (title.isBlank()) {
            errorMessage = "Por favor ingresá un título descriptivo."
          } else {
            onUploadSubmit(title, subject, gradeYear, resourceType, description, simulatedFile)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = SkyPrimary),
        modifier = Modifier.testTag("submit_upload_resource_btn")
      ) {
        Text("Publicar en Biblioteca", color = Color.White)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancelar", color = TextSecondary)
      }
    }
  )
}
