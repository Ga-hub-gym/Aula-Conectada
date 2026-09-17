package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Teacher
import com.example.ui.components.CategoryBadge
import com.example.ui.components.CleanWhiteCard
import com.example.ui.theme.*
import com.example.viewmodel.AulaConectadaUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDirectoryScreen(
  uiState: AulaConectadaUiState,
  onSearchChange: (String) -> Unit,
  onSelectTeacher: (Teacher?) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedSubjectFilter by remember { mutableStateOf("Todas") }
  val subjects = listOf("Todas", "Matemática", "Historia", "Lengua y Literatura", "Filosofía", "Inglés")

  val filteredTeachers = remember(uiState.teachers, uiState.teacherSearchQuery, selectedSubjectFilter) {
    uiState.teachers.filter { teacher ->
      val matchesSearch = uiState.teacherSearchQuery.isBlank() ||
        teacher.name.contains(uiState.teacherSearchQuery, ignoreCase = true) ||
        teacher.subjects.any { it.contains(uiState.teacherSearchQuery, ignoreCase = true) } ||
        teacher.courses.contains(uiState.teacherSearchQuery, ignoreCase = true)

      val matchesSubject = selectedSubjectFilter == "Todas" ||
        teacher.subjects.any { it.contains(selectedSubjectFilter, ignoreCase = true) }

      matchesSearch && matchesSubject
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(SchoolBackground),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Directorio Docente",
            style = MaterialTheme.typography.headlineSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Text(
            text = "Materias, horarios de consulta, criterios y pautas de cursada",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
          )
        }
      }
    }

    // Search bar
    item {
      OutlinedTextField(
        value = uiState.teacherSearchQuery,
        onValueChange = onSearchChange,
        placeholder = { Text("Buscar por profesor, materia o curso...", color = TextMuted) },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = SkyPrimary)
        },
        trailingIcon = {
          if (uiState.teacherSearchQuery.isNotEmpty()) {
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
          .testTag("teacher_search_input")
      )
    }

    // Subject Filter Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(subjects) { subj ->
          FilterChip(
            selected = selectedSubjectFilter == subj,
            onClick = { selectedSubjectFilter = subj },
            label = { Text(subj) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SkyPrimary,
              selectedLabelColor = Color.White
            )
          )
        }
      }
    }

    // Teacher List
    items(filteredTeachers, key = { it.id }) { teacher ->
      CleanWhiteCard(
        onClick = { onSelectTeacher(teacher) },
        modifier = Modifier.testTag("teacher_card_${teacher.id}")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Initials Avatar
          Surface(
            shape = CircleShape,
            color = SkyContainer,
            border = BorderStroke(1.5.dp, SkyPrimary),
            modifier = Modifier.size(52.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = teacher.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = SkyPrimaryDark
                )
              )
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = teacher.name,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            )
            Text(
              text = teacher.title,
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 11.sp
              ),
              maxLines = 1
            )
          }

          Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Ver detalles",
            tint = SkyPrimary,
            modifier = Modifier.size(24.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Subject tags
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          teacher.subjects.forEach { subj ->
            CategoryBadge(
              text = subj,
              containerColor = CyanContainer,
              contentColor = OnCyanContainer
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Consultation snippet
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SchoolBackground,
          border = BorderStroke(1.dp, BorderLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Schedule,
              contentDescription = null,
              tint = SkyPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Consulta: ${teacher.consultationHours}",
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextSecondary,
                fontSize = 11.sp
              ),
              maxLines = 1
            )
          }
        }
      }
    }
  }

  // Teacher Detailed View Dialog
  uiState.selectedTeacher?.let { teacher ->
    TeacherDetailDialog(
      teacher = teacher,
      onDismiss = { onSelectTeacher(null) }
    )
  }
}

@Composable
fun TeacherDetailDialog(
  teacher: Teacher,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(24.dp),
    containerColor = WhiteContainer,
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(SkyContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.School,
            contentDescription = null,
            tint = SkyPrimary,
            modifier = Modifier.size(26.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = teacher.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Text(
            text = teacher.courses,
            style = MaterialTheme.typography.bodySmall.copy(
              color = SkyPrimaryDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          )
        }
      }
    },
    text = {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .heightIn(max = 420.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Consultation hours card
        item {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = SkyContainer.copy(alpha = 0.6f),
            border = BorderStroke(1.dp, SkyPrimary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.AccessTime,
                  contentDescription = null,
                  tint = SkyPrimaryDark,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Horarios de Consulta",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSkyContainer
                  )
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = teacher.consultationHours,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = OnSkyContainer,
                  fontWeight = FontWeight.SemiBold
                )
              )
              Text(
                text = "Lugar: ${teacher.classroom}",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = OnSkyContainer,
                  fontSize = 11.sp
                )
              )
            }
          }
        }

        // Evaluation criteria
        item {
          Text(
            text = "Criterios de Evaluación:",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          teacher.evaluationCriteria.forEach { criterion ->
            Row(
              modifier = Modifier.padding(vertical = 3.dp),
              verticalAlignment = Alignment.Top
            ) {
              Icon(
                imageVector = Icons.Default.FactCheck,
                contentDescription = null,
                tint = SkyPrimary,
                modifier = Modifier.size(16.dp).padding(top = 2.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = criterion,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextSecondary,
                  lineHeight = 18.sp
                )
              )
            }
          }
        }

        // Assignment rules
        item {
          Text(
            text = "Pautas de Entrega de Trabajos:",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          teacher.assignmentRules.forEach { rule ->
            Row(
              modifier = Modifier.padding(vertical = 3.dp),
              verticalAlignment = Alignment.Top
            ) {
              Icon(
                imageVector = Icons.Default.AssignmentTurnedIn,
                contentDescription = null,
                tint = CyanAccent,
                modifier = Modifier.size(16.dp).padding(top = 2.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = rule,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextSecondary,
                  lineHeight = 18.sp
                )
              )
            }
          }
        }

        // Course Update
        item {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = WarningAmberContainer.copy(alpha = 0.6f),
            border = BorderStroke(1.dp, WarningAmber.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Campaign,
                  contentDescription = null,
                  tint = WarningAmber,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Novedades de la Cátedra",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                  )
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = teacher.recentUpdate,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextSecondary,
                  fontSize = 11.sp,
                  lineHeight = 16.sp
                )
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${teacher.email}")
            putExtra(Intent.EXTRA_SUBJECT, "Consulta Cátedra - Aula Conectada")
          }
          try {
            context.startActivity(intent)
          } catch (_: Exception) {
            // Handled gracefully if no email client
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = SkyPrimary)
      ) {
        Icon(
          imageVector = Icons.Default.Email,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Enviar Correo", color = Color.White)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cerrar", color = TextSecondary)
      }
    }
  )
}
