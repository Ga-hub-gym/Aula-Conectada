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
import com.example.model.EsiTopic
import com.example.model.ProtocolStep
import com.example.model.SafeSpaceFaq
import com.example.ui.components.CleanWhiteCard
import com.example.ui.theme.*
import com.example.viewmodel.AulaConectadaUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeSpaceScreen(
  uiState: AulaConectadaUiState,
  onOpenReportDialog: () -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onTagSelected: (String?) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  val tabs = listOf("Protocolo Anti-Bullying", "ESI & Afectividad", "Consultas & FAQ")

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(SchoolBackground)
  ) {
    // Header with Safe Space intro & Confidential Report Button
    Surface(
      color = WhiteContainer,
      border = BorderStroke(1.dp, BorderLight),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SkyContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = SkyPrimary,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Espacio Seguro",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 18.sp,
                  color = TextPrimary
                )
              )
              Text(
                text = "Convivencia, ESI y Gabinete de Orientación",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Confidential Report Hero Callout Button
        Button(
          onClick = onOpenReportDialog,
          colors = ButtonDefaults.buttonColors(containerColor = AlertCrimson),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("open_confidential_report_dialog_btn")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "Enviar Reporte Confidencial al Gabinete",
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              )
              Text(
                text = "Atención privada, respetuosa y opción anónima",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = Color.White.copy(alpha = 0.85f),
                  fontSize = 11.sp
                )
              )
            }
          }
        }
      }
    }

    // Tab bar with sky blue indicator
    TabRow(
      selectedTabIndex = selectedTabIndex,
      containerColor = WhiteContainer,
      contentColor = SkyPrimary,
      divider = { HorizontalDivider(color = BorderLight) }
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTabIndex == index,
          onClick = { selectedTabIndex = index },
          text = {
            Text(
              text = title,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                color = if (selectedTabIndex == index) SkyPrimary else TextMuted
              )
            )
          },
          modifier = Modifier.testTag("safe_space_tab_$index")
        )
      }
    }

    // Tab content
    when (selectedTabIndex) {
      0 -> AntiBullyingProtocolTab(steps = uiState.protocolSteps)
      1 -> EsiSectionTab(topics = uiState.esiTopics)
      2 -> SafeSpaceFaqTab(
        faqs = uiState.safeSpaceFaqs,
        searchQuery = uiState.safeSpaceSearchQuery,
        selectedTag = uiState.selectedSafeSpaceTag,
        onSearchChange = onSearchQueryChange,
        onTagSelect = onTagSelected
      )
    }
  }
}

@Composable
private fun AntiBullyingProtocolTab(steps: List<ProtocolStep>) {
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
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = SkyPrimaryDark,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Guía visual de actuación paso a paso ante situaciones de acoso o ciberacoso entre pares.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = OnSkyContainer,
              fontWeight = FontWeight.Medium,
              lineHeight = 18.sp
            )
          )
        }
      }
    }

    items(steps, key = { it.stepNumber }) { step ->
      CleanWhiteCard(modifier = Modifier.testTag("protocol_step_${step.stepNumber}")) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(SkyPrimary),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${step.stepNumber}",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = step.title,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                fontSize = 15.sp
              )
            )
            Text(
              text = step.subtitle,
              style = MaterialTheme.typography.bodySmall.copy(color = SkyPrimaryDark)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = step.description,
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextSecondary,
            lineHeight = 19.sp
          )
        )

        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SchoolBackground,
          border = BorderStroke(1.dp, BorderLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text(
              text = "Puntos clave de acción:",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            step.keyActions.forEach { action ->
              Row(
                modifier = Modifier.padding(vertical = 2.dp),
                verticalAlignment = Alignment.Top
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = CyanAccent,
                  modifier = Modifier
                    .size(15.dp)
                    .padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = action,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                  )
                )
              }
            }
          }
        }

        step.warningNote?.let { note ->
          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = AlertCrimsonContainer,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = AlertCrimson,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = note,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = AlertCrimson,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium
                )
              )
            }
          }
        }
      }
    }

    // Official Emergency Hotlines Card
    item {
      CleanWhiteCard {
        Text(
          text = "Líneas Gratuitas de Asistencia 24 Horas",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        )
        Spacer(modifier = Modifier.height(8.dp))
        HotlineItem(number = "102", title = "Línea 102", desc = "Derechos y protección de niñas, niños y adolescentes.")
        HotlineItem(number = "144", title = "Línea 144", desc = "Atención, asesoramiento y contención ante situaciones de violencia.")
        HotlineItem(number = "135", title = "Línea 135", desc = "Centro de Asistencia al Suicida y Salud Emocional.")
      }
    }
  }
}

@Composable
private fun HotlineItem(number: String, title: String, desc: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      shape = RoundedCornerShape(8.dp),
      color = CyanContainer,
      modifier = Modifier.size(44.dp, 32.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = number,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = OnCyanContainer
          )
        )
      }
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      )
      Text(
        text = desc,
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextMuted,
          fontSize = 11.sp
        )
      )
    }
  }
}

@Composable
private fun EsiSectionTab(topics: List<EsiTopic>) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = CyanContainer,
        border = BorderStroke(1.dp, CyanAccent.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.VolunteerActivism,
            contentDescription = null,
            tint = CyanAccent,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Educación Sexual Integral (Ley 26.150): Información confiable, afectividad, cuidado del cuerpo y derechos.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = OnCyanContainer,
              fontWeight = FontWeight.Medium,
              lineHeight = 18.sp
            )
          )
        }
      }
    }

    items(topics, key = { it.id }) { topic ->
      EsiExpandableCard(topic = topic)
    }
  }
}

@Composable
private fun EsiExpandableCard(topic: EsiTopic) {
  var isExpanded by remember { mutableStateOf(false) }

  CleanWhiteCard(
    onClick = { isExpanded = !isExpanded },
    modifier = Modifier.testTag("esi_card_${topic.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SkyContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = when (topic.category) {
              "Afectividad" -> Icons.Default.Favorite
              "Cuidado del Cuerpo" -> Icons.Default.HealthAndSafety
              "Derechos y Diversidad" -> Icons.Default.Diversity3
              else -> Icons.Default.VpnKey
            },
            contentDescription = null,
            tint = SkyPrimary,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = topic.title,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Text(
            text = topic.subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
              color = SkyPrimaryDark,
              fontSize = 11.sp
            )
          )
        }
      }

      IconButton(onClick = { isExpanded = !isExpanded }) {
        Icon(
          imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = if (isExpanded) "Colapsar" else "Expandir",
          tint = TextMuted
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = topic.summary,
      style = MaterialTheme.typography.bodySmall.copy(
        color = TextSecondary,
        lineHeight = 18.sp
      )
    )

    AnimatedVisibility(visible = isExpanded) {
      Column(modifier = Modifier.padding(top = 12.dp)) {
        HorizontalDivider(color = BorderLight, modifier = Modifier.padding(bottom = 10.dp))
        Text(
          text = "Ideas y conceptos centrales:",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        topic.keyPoints.forEach { point ->
          Row(
            modifier = Modifier.padding(vertical = 3.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.FiberManualRecord,
              contentDescription = null,
              tint = CyanAccent,
              modifier = Modifier
                .size(10.dp)
                .padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = point,
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextSecondary,
                lineHeight = 18.sp
              )
            )
          }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = SchoolBackground,
          border = BorderStroke(1.dp, BorderLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "Marco de Referencia: ${topic.legalReference}",
            style = MaterialTheme.typography.labelSmall.copy(
              color = TextMuted,
              fontSize = 11.sp
            ),
            modifier = Modifier.padding(8.dp)
          )
        }
      }
    }
  }
}

@Composable
private fun SafeSpaceFaqTab(
  faqs: List<SafeSpaceFaq>,
  searchQuery: String,
  selectedTag: String?,
  onSearchChange: (String) -> Unit,
  onTagSelect: (String?) -> Unit
) {
  val allTags = listOf("#Derechos", "#Acoso", "#SaludMental", "#Convivencia", "#ESI")

  val filteredFaqs = remember(faqs, searchQuery, selectedTag) {
    faqs.filter { faq ->
      val matchesQuery = searchQuery.isBlank() ||
        faq.question.contains(searchQuery, ignoreCase = true) ||
        faq.answer.contains(searchQuery, ignoreCase = true)
      val matchesTag = selectedTag == null || faq.tags.contains(selectedTag)
      matchesQuery && matchesTag
    }
  }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Search Bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("safe_space_faq_search_input"),
        placeholder = { Text("Buscar preguntas o dudas...", color = TextMuted) },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = SkyPrimary)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
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
        singleLine = true
      )
    }

    // Filter Tag Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        item {
          FilterChip(
            selected = selectedTag == null,
            onClick = { onTagSelect(null) },
            label = { Text("Todos") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SkyPrimary,
              selectedLabelColor = Color.White
            )
          )
        }
        items(allTags) { tag ->
          FilterChip(
            selected = selectedTag == tag,
            onClick = { onTagSelect(tag) },
            label = { Text(tag) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SkyPrimary,
              selectedLabelColor = Color.White
            ),
            modifier = Modifier.testTag("tag_chip_$tag")
          )
        }
      }
    }

    if (filteredFaqs.isEmpty()) {
      item {
        CleanWhiteCard {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.SearchOff,
              contentDescription = null,
              tint = TextMuted,
              modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "No se encontraron preguntas para este filtro.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
          }
        }
      }
    } else {
      items(filteredFaqs, key = { it.id }) { faq ->
        SafeSpaceFaqCard(faq = faq)
      }
    }
  }
}

@Composable
private fun SafeSpaceFaqCard(faq: SafeSpaceFaq) {
  var isExpanded by remember { mutableStateOf(false) }

  CleanWhiteCard(
    onClick = { isExpanded = !isExpanded },
    modifier = Modifier.testTag("safe_faq_card_${faq.id}")
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

    Spacer(modifier = Modifier.height(6.dp))

    // Tag pills
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
      faq.tags.forEach { tag ->
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = CyanContainer,
          modifier = Modifier.padding(vertical = 2.dp)
        ) {
          Text(
            text = tag,
            style = MaterialTheme.typography.labelSmall.copy(
              color = OnCyanContainer,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }
    }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfidentialReportDialog(
  onDismiss: () -> Unit,
  onSubmitReport: (situationType: String, gradeCourse: String, isAnonymous: Boolean, contactInfo: String, urgency: String, message: String) -> Unit
) {
  var isAnonymous by remember { mutableStateOf(true) }
  var contactInfo by remember { mutableStateOf("") }
  var gradeCourse by remember { mutableStateOf("4° Año B") }
  var situationType by remember { mutableStateOf("Acoso / Hostigamiento escolar") }
  var urgencyLevel by remember { mutableStateOf("Media") }
  var message by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  val situationTypes = listOf(
    "Acoso / Hostigamiento escolar",
    "Ciberacoso en redes sociales",
    "Situación familiar o vulnerabilidad",
    "Vulneración de derechos / Discriminación",
    "Acompañamiento psicopedagógico"
  )

  val urgencyLevels = listOf("Baja", "Media", "Urgente")

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
            .background(AlertCrimsonContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = AlertCrimson,
            modifier = Modifier.size(22.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Reporte Confidencial",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Text(
            text = "Directo al Gabinete de Orientación Escolar",
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              fontSize = 11.sp
            )
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
              imageVector = Icons.Default.VerifiedUser,
              contentDescription = null,
              tint = SuccessEmerald,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Tu relato se tratará con estricta reserva profesional.",
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextSecondary,
                fontSize = 11.sp
              )
            )
          }
        }

        // Anonymous toggle
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Modo Anónimo",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            )
            Text(
              text = if (isAnonymous) "No se asociará tu nombre ni contacto" else "Compartirás tu contacto para seguimiento",
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 11.sp
              )
            )
          }
          Switch(
            checked = isAnonymous,
            onCheckedChange = { isAnonymous = it },
            colors = SwitchDefaults.colors(
              checkedThumbColor = SkyPrimary,
              checkedTrackColor = SkyContainer
            ),
            modifier = Modifier.testTag("anonymous_switch")
          )
        }

        if (!isAnonymous) {
          OutlinedTextField(
            value = contactInfo,
            onValueChange = { contactInfo = it },
            label = { Text("Tu nombre o teléfono de contacto") },
            placeholder = { Text("Ej: Camila Torres - 11-4567-8900") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().testTag("contact_info_input"),
            singleLine = true
          )
        }

        OutlinedTextField(
          value = gradeCourse,
          onValueChange = { gradeCourse = it },
          label = { Text("Año y División relacionada") },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("grade_course_input"),
          singleLine = true
        )

        // Situation Type selector
        Column {
          Text(
            text = "Tipo de situación:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(situationTypes) { type ->
              FilterChip(
                selected = situationType == type,
                onClick = { situationType = type },
                label = { Text(type, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SkyPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }

        // Urgency Level chips
        Column {
          Text(
            text = "Nivel de urgencia:",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            urgencyLevels.forEach { level ->
              FilterChip(
                selected = urgencyLevel == level,
                onClick = { urgencyLevel = level },
                label = { Text(level) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = when (level) {
                    "Urgente" -> AlertCrimson
                    "Media" -> WarningAmber
                    else -> SuccessEmerald
                  },
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }

        // Message input
        OutlinedTextField(
          value = message,
          onValueChange = {
            message = it
            errorMessage = null
          },
          label = { Text("Detalle de la situación") },
          placeholder = { Text("Contanos qué ocurrió, cuándo o qué necesitás del equipo...") },
          minLines = 3,
          maxLines = 5,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("report_message_input"),
          isError = errorMessage != null
        )

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
          if (message.isBlank()) {
            errorMessage = "Por favor escribí una breve descripción de la situación."
          } else {
            onSubmitReport(situationType, gradeCourse, isAnonymous, contactInfo, urgencyLevel, message)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = AlertCrimson),
        modifier = Modifier.testTag("submit_confidential_report_btn")
      ) {
        Text("Enviar Reporte Discreto", color = Color.White)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancelar", color = TextSecondary)
      }
    }
  )
}
