package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InstitutionalContact
import com.example.model.SchoolFaqItem
import com.example.ui.components.CategoryBadge
import com.example.ui.components.CleanWhiteCard
import com.example.ui.theme.*
import com.example.viewmodel.AulaConectadaUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolFaqContactScreen(
  uiState: AulaConectadaUiState,
  onCategorySelect: (String) -> Unit,
  onSearchChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Reglamento & Trámites", "Directorio Institucional")
  val categories = listOf("Todos", "Reglamento y Convivencia", "Asistencias y Faltas", "Certificados y Trámites")

  val filteredFaqs = remember(uiState.schoolFaqs, uiState.selectedSchoolFaqCategory, uiState.schoolFaqSearchQuery) {
    uiState.schoolFaqs.filter { faq ->
      val matchesSearch = uiState.schoolFaqSearchQuery.isBlank() ||
        faq.question.contains(uiState.schoolFaqSearchQuery, ignoreCase = true) ||
        faq.answer.contains(uiState.schoolFaqSearchQuery, ignoreCase = true)
      val matchesCat = uiState.selectedSchoolFaqCategory == "Todos" ||
        faq.category == uiState.selectedSchoolFaqCategory
      matchesSearch && matchesCat
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(SchoolBackground)
  ) {
    // Header
    Surface(
      color = WhiteContainer,
      border = BorderStroke(1.dp, BorderLight),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "Preguntas Frecuentes & Contacto",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextPrimary
          )
        )
        Text(
          text = "Régimen académico, asistencias, certificados y vías institucionales",
          style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
        )
      }
    }

    // Tabs
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = WhiteContainer,
      contentColor = SkyPrimary,
      divider = { HorizontalDivider(color = BorderLight) }
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Text(
              text = title,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                color = if (selectedTab == index) SkyPrimary else TextMuted
              )
            )
          },
          modifier = Modifier.testTag("faq_contact_tab_$index")
        )
      }
    }

    if (selectedTab == 0) {
      // FAQ List
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Search bar
        item {
          OutlinedTextField(
            value = uiState.schoolFaqSearchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Buscar normativa, faltas, certificados...", color = TextMuted) },
            leadingIcon = {
              Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = SkyPrimary)
            },
            trailingIcon = {
              if (uiState.schoolFaqSearchQuery.isNotEmpty()) {
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
              .testTag("school_faq_search_input")
          )
        }

        // Category Chips
        item {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            items(categories) { cat ->
              FilterChip(
                selected = uiState.selectedSchoolFaqCategory == cat,
                onClick = { onCategorySelect(cat) },
                label = { Text(cat) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SkyPrimary,
                  selectedLabelColor = Color.White
                ),
                modifier = Modifier.testTag("faq_cat_chip_$cat")
              )
            }
          }
        }

        items(filteredFaqs, key = { it.id }) { faq ->
          SchoolFaqCard(faq = faq)
        }
      }
    } else {
      // Institutional Directory List
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item {
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = SkyContainer,
            border = BorderStroke(1.dp, SkyPrimary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.ContactSupport,
                contentDescription = null,
                tint = SkyPrimaryDark,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Comunicate directamente con las áreas directivas, de orientación escolar, secretaría y preceptoría.",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = OnSkyContainer,
                  lineHeight = 18.sp
                )
              )
            }
          }
        }

        items(uiState.institutionalContacts, key = { it.id }) { contact ->
          InstitutionalContactCard(contact = contact)
        }
      }
    }
  }
}

@Composable
private fun SchoolFaqCard(faq: SchoolFaqItem) {
  var isExpanded by remember { mutableStateOf(false) }

  CleanWhiteCard(
    onClick = { isExpanded = !isExpanded },
    modifier = Modifier.testTag("school_faq_card_${faq.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = faq.question,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        ),
        modifier = Modifier.weight(1f)
      )
      IconButton(onClick = { isExpanded = !isExpanded }) {
        Icon(
          imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = TextMuted
        )
      }
    }

    Spacer(modifier = Modifier.height(4.dp))
    CategoryBadge(
      text = faq.category,
      containerColor = CyanContainer,
      contentColor = OnCyanContainer
    )

    AnimatedVisibility(visible = isExpanded) {
      Column(modifier = Modifier.padding(top = 10.dp)) {
        HorizontalDivider(color = BorderLight, modifier = Modifier.padding(bottom = 8.dp))
        Text(
          text = faq.answer,
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextSecondary,
            lineHeight = 20.sp
          )
        )
      }
    }
  }
}

@Composable
private fun InstitutionalContactCard(contact: InstitutionalContact) {
  val context = LocalContext.current

  CleanWhiteCard(modifier = Modifier.testTag("contact_card_${contact.id}")) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(SkyContainer),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = when (contact.id) {
            "c-1" -> Icons.Default.AccountBalance
            "c-2" -> Icons.Default.Assignment
            "c-3" -> Icons.Default.Psychology
            "c-4" -> Icons.Default.Badge
            else -> Icons.Default.Groups
          },
          contentDescription = null,
          tint = SkyPrimary,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = contact.department,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            fontSize = 15.sp
          )
        )
        Text(
          text = contact.role,
          style = MaterialTheme.typography.bodySmall.copy(
            color = SkyPrimaryDark,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "A cargo: ${contact.personInCharge}",
      style = MaterialTheme.typography.bodySmall.copy(
        color = TextSecondary,
        fontWeight = FontWeight.SemiBold
      )
    )

    Spacer(modifier = Modifier.height(4.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.Default.Place,
        contentDescription = null,
        tint = TextMuted,
        modifier = Modifier.size(15.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = contact.office,
        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
      )
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(top = 2.dp)
    ) {
      Icon(
        imageVector = Icons.Default.AccessTime,
        contentDescription = null,
        tint = TextMuted,
        modifier = Modifier.size(15.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = contact.schedule,
        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    HorizontalDivider(color = BorderLight)

    Spacer(modifier = Modifier.height(10.dp))

    // Action buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      OutlinedButton(
        onClick = {
          val cleanPhone = contact.phone.filter { it.isDigit() || it == '+' }
          val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$cleanPhone")
          }
          try {
            context.startActivity(intent)
          } catch (_: Exception) {}
        },
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = SkyPrimary)
      ) {
        Icon(
          imageVector = Icons.Default.Phone,
          contentDescription = "Llamar",
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Llamar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }

      Button(
        onClick = {
          val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${contact.email}")
            putExtra(Intent.EXTRA_SUBJECT, "Consulta Institucional - Aula Conectada")
          }
          try {
            context.startActivity(intent)
          } catch (_: Exception) {}
        },
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SkyPrimary)
      ) {
        Icon(
          imageVector = Icons.Default.Mail,
          contentDescription = "Correo",
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Correo", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}
