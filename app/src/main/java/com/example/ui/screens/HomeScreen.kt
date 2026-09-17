package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Announcement
import com.example.model.AppNavTab
import com.example.ui.components.CategoryBadge
import com.example.ui.components.CleanWhiteCard
import com.example.ui.theme.*
import com.example.viewmodel.AulaConectadaUiState

@Composable
fun HomeScreen(
  uiState: AulaConectadaUiState,
  onNavigateTab: (AppNavTab) -> Unit,
  onOpenConfidentialReport: () -> Unit,
  onSelectAnnouncement: (Announcement) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(SchoolBackground),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Welcome & Context Hero Banner
    item {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = WhiteContainer,
        border = BorderStroke(1.dp, BorderLight),
        shadowElevation = 0.5.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "¡Hola, ${uiState.userProfile.name.substringBefore(" ")}!",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
              )
              Text(
                text = "${uiState.userProfile.gradeYear} • Ciclo Lectivo 2026",
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = SkyPrimary,
                  fontWeight = FontWeight.SemiBold
                )
              )
            }
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(SkyContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = null,
                tint = SkyPrimary,
                modifier = Modifier.size(24.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Conectate con tus compañeros, descargá material de estudio y accedé a un canal seguro y cuidado para toda la comunidad.",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = TextSecondary,
              lineHeight = 20.sp
            )
          )
        }
      }
    }

    // Quick Access Cards Section
    item {
      Column {
        Text(
          text = "Accesos Rápidos",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            fontSize = 18.sp
          ),
          modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          QuickAccessCard(
            title = "Espacio Seguro",
            subtitle = "ESI & Convivencia",
            icon = Icons.Default.Shield,
            accentColor = SkyPrimary,
            containerColor = SkyContainer,
            testTag = "quick_access_safe_space",
            onClick = { onNavigateTab(AppNavTab.SAFE_SPACE) },
            modifier = Modifier.weight(1f)
          )
          QuickAccessCard(
            title = "Biblioteca",
            subtitle = "Apuntes y Modelos",
            icon = Icons.Default.MenuBook,
            accentColor = CyanAccent,
            containerColor = CyanContainer,
            testTag = "quick_access_library",
            onClick = { onNavigateTab(AppNavTab.LIBRARY) },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          QuickAccessCard(
            title = "Profesores",
            subtitle = "Consultas & Pautas",
            icon = Icons.Default.People,
            accentColor = Color(0xFF0284C7),
            containerColor = Color(0xFFE0F2FE),
            testTag = "quick_access_teachers",
            onClick = { onNavigateTab(AppNavTab.TEACHERS) },
            modifier = Modifier.weight(1f)
          )
          QuickAccessCard(
            title = "Reporte",
            subtitle = "Al Gabinete (Privado)",
            icon = Icons.Default.Lock,
            accentColor = AlertCrimson,
            containerColor = AlertCrimsonContainer,
            testTag = "quick_access_confidential_report",
            onClick = onOpenConfidentialReport,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Announcements Section Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Novedades y Anuncios Escolares",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary,
              fontSize = 18.sp
            )
          )
          Text(
            text = "Fechas clave, eventos solidarios y exámenes",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
          )
        }
        Icon(
          imageVector = Icons.Default.Campaign,
          contentDescription = null,
          tint = SkyPrimary,
          modifier = Modifier.size(24.dp)
        )
      }
    }

    // Announcements list in white cards with light borders
    items(uiState.announcements, key = { it.id }) { announcement ->
      CleanWhiteCard(
        onClick = { onSelectAnnouncement(announcement) },
        modifier = Modifier.testTag("announcement_card_${announcement.id}")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            CategoryBadge(
              text = announcement.category,
              containerColor = when (announcement.category) {
                "Exámenes" -> AlertCrimsonContainer
                "Solidaridad" -> CyanContainer
                "Eventos" -> SkyContainer
                else -> WarningAmberContainer
              },
              contentColor = when (announcement.category) {
                "Exámenes" -> AlertCrimson
                "Solidaridad" -> OnCyanContainer
                "Eventos" -> OnSkyContainer
                else -> WarningAmber
              }
            )
            if (announcement.isImportant) {
              Spacer(modifier = Modifier.width(8.dp))
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = AlertCrimson.copy(alpha = 0.1f)
              ) {
                Text(
                  text = "Prioritario",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = AlertCrimson,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                  ),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }

          Text(
            text = announcement.date,
            style = MaterialTheme.typography.labelSmall.copy(
              color = TextMuted,
              fontWeight = FontWeight.Medium
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = announcement.title,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            fontSize = 15.sp,
            lineHeight = 20.sp
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = announcement.summary,
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextSecondary,
            lineHeight = 18.sp
          ),
          maxLines = 2
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Place,
              contentDescription = null,
              tint = TextMuted,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = announcement.location,
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 12.sp
              )
            )
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onSelectAnnouncement(announcement) }
          ) {
            Text(
              text = "Ver detalles",
              style = MaterialTheme.typography.labelSmall.copy(
                color = SkyPrimary,
                fontWeight = FontWeight.Bold
              )
            )
            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = null,
              tint = SkyPrimary,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun QuickAccessCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  containerColor: Color,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = WhiteContainer,
    border = BorderStroke(1.dp, BorderLight),
    shadowElevation = 0.5.dp,
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .testTag(testTag)
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(containerColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          color = TextPrimary,
          fontSize = 14.sp
        )
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextMuted,
          fontSize = 11.sp
        ),
        maxLines = 1
      )
    }
  }
}
