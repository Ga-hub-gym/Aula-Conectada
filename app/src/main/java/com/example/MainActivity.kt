package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.AppNavTab
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.AulaConectadaViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: AulaConectadaViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        AulaConectadaApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun AulaConectadaApp(viewModel: AulaConectadaViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(uiState.userFeedbackMessage) {
    uiState.userFeedbackMessage?.let { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
      viewModel.clearFeedbackMessage()
    }
  }

  Scaffold(
    topBar = {
      SchoolTopAppBar(
        userProfile = uiState.userProfile,
        onProfileClick = { viewModel.setProfileDialogOpen(true) }
      )
    },
    bottomBar = {
      AulaBottomNavigationBar(
        currentTab = uiState.currentTab,
        onTabSelected = { viewModel.selectTab(it) }
      )
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(
          snackbarData = data,
          containerColor = TextPrimary,
          contentColor = Color.White,
          shape = MaterialTheme.shapes.medium
        )
      }
    },
    modifier = Modifier
      .fillMaxSize()
      .background(SchoolBackground)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (uiState.currentTab) {
        AppNavTab.HOME -> {
          HomeScreen(
            uiState = uiState,
            onNavigateTab = { viewModel.selectTab(it) },
            onOpenConfidentialReport = { viewModel.openConfidentialReportDialog() },
            onSelectAnnouncement = { viewModel.selectAnnouncement(it) }
          )
        }

        AppNavTab.SAFE_SPACE -> {
          SafeSpaceScreen(
            uiState = uiState,
            onOpenReportDialog = { viewModel.openConfidentialReportDialog() },
            onSearchQueryChange = { viewModel.setSafeSpaceSearchQuery(it) },
            onTagSelected = { viewModel.selectSafeSpaceTag(it) }
          )
        }

        AppNavTab.LIBRARY -> {
          LibraryScreen(
            uiState = uiState,
            onSearchChange = { viewModel.setLibrarySearchQuery(it) },
            onYearFilterSelect = { viewModel.setLibraryGradeYearFilter(it) },
            onSubjectFilterSelect = { viewModel.setLibrarySubjectFilter(it) },
            onToggleFavorite = { viewModel.toggleFavoriteResource(it) },
            onRateResource = { id, stars -> viewModel.rateResource(id, stars) },
            onDownloadResource = { viewModel.downloadResource(it) },
            onOpenUploadDialog = { viewModel.openUploadDialog() },
            onCloseUploadDialog = { viewModel.closeUploadDialog() },
            onUploadSubmit = { title, subj, yr, type, desc, file ->
              viewModel.uploadResource(title, subj, yr, type, desc, file)
            }
          )
        }

        AppNavTab.TEACHERS -> {
          TeacherDirectoryScreen(
            uiState = uiState,
            onSearchChange = { viewModel.setTeacherSearchQuery(it) },
            onSelectTeacher = { viewModel.selectTeacher(it) }
          )
        }

        AppNavTab.FAQ_CONTACT -> {
          SchoolFaqContactScreen(
            uiState = uiState,
            onCategorySelect = { viewModel.setSchoolFaqCategory(it) },
            onSearchChange = { viewModel.setSchoolFaqSearchQuery(it) }
          )
        }
      }
    }
  }

  // Dialogs
  if (uiState.isProfileDialogOpen) {
    ProfileDialog(
      userProfile = uiState.userProfile,
      onDismiss = { viewModel.setProfileDialogOpen(false) }
    )
  }

  uiState.selectedAnnouncement?.let { ann ->
    AnnouncementDetailDialog(
      announcement = ann,
      onDismiss = { viewModel.selectAnnouncement(null) }
    )
  }

  if (uiState.isConfidentialReportDialogOpen) {
    ConfidentialReportDialog(
      onDismiss = { viewModel.closeConfidentialReportDialog() },
      onSubmitReport = { situation, grade, anon, contact, urgency, msg ->
        viewModel.submitConfidentialReport(situation, grade, anon, contact, urgency, msg)
      }
    )
  }
}

@Composable
fun AulaBottomNavigationBar(
  currentTab: AppNavTab,
  onTabSelected: (AppNavTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    containerColor = WhiteContainer,
    tonalElevation = 2.dp,
    windowInsets = WindowInsets.navigationBars,
    modifier = modifier.testTag("main_bottom_nav_bar")
  ) {
    val items = listOf(
      BottomNavItem(AppNavTab.HOME, "Inicio", Icons.Default.Home, Icons.Outlined.Home, "nav_home"),
      BottomNavItem(AppNavTab.SAFE_SPACE, "Seguro", Icons.Default.Shield, Icons.Outlined.Shield, "nav_safe_space"),
      BottomNavItem(AppNavTab.LIBRARY, "Biblioteca", Icons.Default.MenuBook, Icons.Outlined.MenuBook, "nav_library"),
      BottomNavItem(AppNavTab.TEACHERS, "Profesores", Icons.Default.People, Icons.Outlined.People, "nav_teachers"),
      BottomNavItem(AppNavTab.FAQ_CONTACT, "Contacto", Icons.Default.Help, Icons.Outlined.HelpOutline, "nav_faq_contact")
    )

    items.forEach { item ->
      val selected = currentTab == item.tab
      NavigationBarItem(
        selected = selected,
        onClick = { onTabSelected(item.tab) },
        icon = {
          Icon(
            imageVector = if (selected) item.filledIcon else item.outlinedIcon,
            contentDescription = item.label,
            modifier = Modifier.size(22.dp)
          )
        },
        label = {
          Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
              fontSize = 10.sp
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = SkyPrimaryDark,
          selectedTextColor = SkyPrimaryDark,
          indicatorColor = SkyContainer,
          unselectedIconColor = TextMuted,
          unselectedTextColor = TextMuted
        ),
        modifier = Modifier.testTag(item.testTag)
      )
    }
  }
}

private data class BottomNavItem(
  val tab: AppNavTab,
  val label: String,
  val filledIcon: ImageVector,
  val outlinedIcon: ImageVector,
  val testTag: String
)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}

