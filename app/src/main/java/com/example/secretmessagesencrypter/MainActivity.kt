package com.example.secretmessagesencrypter

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.secretmessagesencrypter.precentation.screen.drawer.DrawerScreen
import com.example.secretmessagesencrypter.precentation.screen.encryption.EncryptionApp
import com.example.secretmessagesencrypter.precentation.screen.EncryptionViewModel
import com.example.secretmessagesencrypter.precentation.screen.decryption.DecryptionScreen
import com.example.secretmessagesencrypter.precentation.screen.settings.SettingsScreen
import com.example.secretmessagesencrypter.precentation.screen.settings.SettingsViewModel
import com.example.secretmessagesencrypter.precentation.screen.settings.enums.AppearanceMode
import com.example.secretmessagesencrypter.precentation.ui.theme.SecretMessagesEncrypterTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val encryptionViewModel = koinViewModel<EncryptionViewModel>()
            val settingsViewModel = koinViewModel<SettingsViewModel>()


            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val showEncryptionScreen = rememberSaveable { mutableStateOf(true) }
            val showSettingsDialog = rememberSaveable { mutableStateOf(false) }

            BackHandler {
                when {
                    drawerState.isOpen -> {
                        scope.launch {
                            drawerState.close()
                        }
                    }

                    else -> {
                        finish()
                    }
                }
            }

            val theme = settingsViewModel.darkMode.collectAsStateWithLifecycle()

            val currentTheme = when (theme.value) {
                AppearanceMode.LIGHT.key -> false
                AppearanceMode.DARK.key -> true
                else -> isSystemInDarkTheme()
            }

            val view = LocalView.current
            if (!view.isInEditMode) {
                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor =
                        Color.Transparent.toArgb() // this is for status bar color
                    window.navigationBarColor =
                        Color.Transparent.toArgb() //this is for bottom system navigation bar color
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                        !currentTheme // this is for status bar text color
                }
            }

            SecretMessagesEncrypterTheme(darkTheme = currentTheme) {

                ModalNavigationDrawer(
                    modifier = Modifier.fillMaxSize(),
                    gesturesEnabled = true,
                    drawerContent = {
                        DrawerScreen(
//                            username = settingsViewModel.username,
                            encryptionClicked = {
                                showEncryptionScreen.value =
                                    true; scope.launch { drawerState.close() }
                            },
                            decryptionClicked = {
                                showEncryptionScreen.value =
                                    false; scope.launch { drawerState.close() }
                            },
                            settingsClicked = {
                                showSettingsDialog.value =
                                    true; scope.launch { drawerState.close() }
                            }
                        )
                    },
                    drawerState = drawerState,
                    content = {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                TopAppBar(
                                    navigationIcon = {
                                        IconButton(
                                            onClick = { scope.launch { drawerState.open() } }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Menu,
                                                contentDescription = "menu"
                                            )
                                        }
                                    },
                                    title = {
                                        Text(
                                            "Secret's by Sultan \uD83E\uDD0D",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                )
                            }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                if (showEncryptionScreen.value) {
                                    EncryptionApp(viewModel = encryptionViewModel, settingsViewModel = settingsViewModel)
                                } else {
                                    DecryptionScreen(viewModel = encryptionViewModel)
                                }
                            }
                        }
                    }
                )

                if (showSettingsDialog.value) {
                    BasicAlertDialog(
                        modifier = Modifier,
                        onDismissRequest = { showSettingsDialog.value = false },
                        properties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = false,
                            usePlatformDefaultWidth = true
                        ),
                        content = {
                            ElevatedCard(modifier = Modifier.padding(16.dp)) {
                                SettingsScreen(
                                    viewModel = settingsViewModel,
                                    closeDialog = { showSettingsDialog.value = false },
                                    saveClicked = { settingsViewModel.loadSettings(); showSettingsDialog.value = false}
                                )
                            }
                        },
                    )
                }


            }
        }
    }
}
