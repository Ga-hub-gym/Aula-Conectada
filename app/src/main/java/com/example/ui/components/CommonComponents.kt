package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.model.Announcement
import com.example.model.UserProfile
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolTopAppBar(
  userProfile: UserProfile,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    color = WhiteContainer,
    tonalElevation = 1.dp,
    shadowElevation = 1.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SkyContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.School,
            contentDescription = "Logo Aula Conectada",
            tint = SkyPrimary,
            modifier = Modifier.size(24.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Aula Conectada",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 19.sp,
              color = TextPrimary
            )
          )
          Text(
            text = userProfile.schoolName,
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            ),
            maxLines = 1
          )
        }
      }

      // User avatar button
      Surface(
        onClick = onProfileClick,
        shape = CircleShape,
        color = CyanContainer,
        border = BorderStroke(1.5.dp, SkyPrimary),
        modifier = Modifier
          .size(42.dp)
          .testTag("profile_avatar_button")
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(
            text = userProfile.name.take(2).uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              color = OnCyanContainer
            )
          )
        }
      }
    }
  }
}

@Composable
fun CategoryBadge(
  text: String,
  modifier: Modifier = Modifier,
  containerColor: Color = SkyContainer,
  contentColor: Color = OnSkyContainer
) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = containerColor,
    modifier = modifier
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        color = contentColor
      ),
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
  }
}

@Composable
fun CleanWhiteCard(
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
  borderColor: Color = BorderLight,
  content: @Composable ColumnScope.() -> Unit
) {
  val cardModifier = if (onClick != null) {
    modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
  } else {
    modifier.fillMaxWidth()
  }

  Surface(
    shape = RoundedCornerShape(16.dp),
    color = WhiteContainer,
    border = BorderStroke(1.dp, borderColor),
    shadowElevation = 0.5.dp,
    modifier = cardModifier
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      content = content
    )
  }
}

@Composable
fun StarRatingBar(
  rating: Float,
  ratingCount: Int,
  onRateClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.then(
      if (onRateClick != null) Modifier.clickable(onClick = onRateClick) else Modifier
    )
  ) {
    Icon(
      imageVector = Icons.Default.Star,
      contentDescription = "Rating",
      tint = Color(0xFFEAB308), // Vibrant Amber
      modifier = Modifier.size(16.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = String.format("%.1f", rating),
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    )
    Text(
      text = " ($ratingCount)",
      style = MaterialTheme.typography.bodySmall.copy(
        color = TextMuted,
        fontSize = 11.sp
      )
    )
  }
}

@Composable
fun RatingDialog(
  currentRating: Float,
  onDismiss: () -> Unit,
  onRateSubmit: (Int) -> Unit
) {
  var selectedStars by remember { mutableIntStateOf(5) }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = WhiteContainer,
    title = {
      Text(
        text = "Calificar recurso de estudio",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      )
    },
    text = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
      ) {
        Text(
          text = "¿Qué tan útil te resultó este material para estudiar?",
          style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
          modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxWidth()
        ) {
          (1..5).forEach { star ->
            IconButton(
              onClick = { selectedStars = star },
              modifier = Modifier.size(48.dp)
            ) {
              Icon(
                imageVector = if (star <= selectedStars) Icons.Default.Star else Icons.Outlined.StarOutline,
                contentDescription = "$star estrellas",
                tint = if (star <= selectedStars) Color(0xFFEAB308) else BorderMedium,
                modifier = Modifier.size(36.dp)
              )
            }
          }
        }
        Text(
          text = when (selectedStars) {
            5 -> "¡Excelente material!"
            4 -> "Muy buen apunte"
            3 -> "Útil y completo"
            2 -> "Podría mejorar"
            else -> "Incompleto"
          },
          style = MaterialTheme.typography.labelLarge.copy(
            color = SkyPrimary,
            fontWeight = FontWeight.SemiBold
          ),
          modifier = Modifier.padding(top = 8.dp)
        )
      }
    },
    confirmButton = {
      Button(
        onClick = { onRateSubmit(selectedStars) },
        colors = ButtonDefaults.buttonColors(containerColor = SkyPrimary)
      ) {
        Text("Confirmar Calificación", color = Color.White)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancelar", color = TextSecondary)
      }
    }
  )
}

@Composable
fun ProfileDialog(
  userProfile: UserProfile,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(24.dp),
    containerColor = WhiteContainer,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(SkyContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Avatar",
            tint = SkyPrimary,
            modifier = Modifier.size(32.dp)
          )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
          Text(
            text = userProfile.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          )
          Text(
            text = "${userProfile.gradeYear} • ${userProfile.division}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = SkyPrimaryDark,
              fontWeight = FontWeight.Medium
            )
          )
        }
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        HorizontalDivider(color = BorderLight, modifier = Modifier.padding(bottom = 12.dp))
        ProfileInfoRow(label = "Institución", value = userProfile.schoolName, icon = Icons.Default.School)
        ProfileInfoRow(label = "Condición", value = userProfile.role, icon = Icons.Default.Badge)
        ProfileInfoRow(label = "Legajo Estudiantil", value = userProfile.studentId, icon = Icons.Default.ConfirmationNumber)
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = CyanContainer.copy(alpha = 0.5f),
          border = BorderStroke(1.dp, CyanAccent.copy(alpha = 0.3f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = CyanAccent,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Cuenta activa protegida por acuerdos de convivencia escolar.",
              style = MaterialTheme.typography.bodySmall.copy(
                color = OnCyanContainer,
                fontSize = 11.sp
              )
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = SkyPrimary)
      ) {
        Text("Cerrar", color = Color.White)
      }
    }
  )
}

@Composable
private fun ProfileInfoRow(
  label: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = TextMuted,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 11.sp)
      )
      Text(
        text = value,
        style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary, fontWeight = FontWeight.Medium)
      )
    }
  }
}

@Composable
fun AnnouncementDetailDialog(
  announcement: Announcement,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = WhiteContainer,
    title = {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          CategoryBadge(text = announcement.category)
          Text(
            text = announcement.date,
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = announcement.title,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        )
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = announcement.fullDetails,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = TextSecondary,
            lineHeight = 22.sp
          )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = SchoolBackground,
          border = BorderStroke(1.dp, BorderLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Ubicación",
                tint = SkyPrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = announcement.location,
                style = MaterialTheme.typography.bodySmall.copy(color = TextPrimary, fontWeight = FontWeight.Medium)
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Group,
                contentDescription = "Organizador",
                tint = CyanAccent,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Organiza: ${announcement.organizer}",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = SkyPrimary)
      ) {
        Text("Entendido", color = Color.White)
      }
    }
  )
}
