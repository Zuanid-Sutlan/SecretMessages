package com.example.secretmessagesencrypter.precentation.screen.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TextButton
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.secretmessagesencrypter.precentation.screen.drawer.ModifiedTextButton
import com.example.secretmessagesencrypter.precentation.screen.settings.enums.AppearanceMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    saveClicked: () -> Unit,
    closeDialog: () -> Unit
) {
    val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()
//    val username by viewModel.username.collectAsStateWithLifecycle()
//    val encryptionKey by viewModel.encryptionKey.collectAsStateWithLifecycle()

    var username by rememberSaveable { mutableStateOf("") }
    var encryptionKey by rememberSaveable { mutableStateOf("") }

    val showAppearanceDialog = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Appearance Mode
        val mode = when (darkMode) {
            AppearanceMode.LIGHT.key -> "Light"
            AppearanceMode.DARK.key -> "Dark"
            else -> "System Default"
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Appearance Mode", fontSize = 18.sp)
            TextButton(
                onClick = {
                    showAppearanceDialog.value = true
                }
            ) {
                Text(text = mode, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }

        // Username Input
        OutlinedTextField(
            value = username,
            onValueChange = { username = it }, // viewModel.setUsername(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        // Custom encryption key
        OutlinedTextField(
            value = encryptionKey,
            onValueChange = { encryptionKey = it }, // viewModel.setUsername(it) },
            label = { Text("Enter your Custom encryption key") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                scope.launch {
                    viewModel.setUsername(username)
                    viewModel.setEncryptionKey(encryptionKey)
                    saveClicked()
                }
            }
        ) { Text(text = "Apply/Save") }

    }

    AnimatedVisibility(modifier = Modifier.fillMaxWidth(), visible = showAppearanceDialog.value) {
        BasicAlertDialog(
            modifier = Modifier,
            onDismissRequest = { showAppearanceDialog.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = true
            ),
            content = {
                ElevatedCard(modifier = Modifier.padding(16.dp)) {
                    Column {
                        ModifiedTextButton(
                            text = "Light Theme",
                            onClick = {
                                viewModel.setTheme(AppearanceMode.LIGHT.key)
                                showAppearanceDialog.value = false
                            }
                        )
                        HorizontalDivider()
                        ModifiedTextButton(
                            text = "Dark Theme",
                            onClick = {
                                viewModel.setTheme(AppearanceMode.DARK.key)
                                showAppearanceDialog.value = false
                            }
                        )
                        HorizontalDivider()
                        ModifiedTextButton(
                            text = "System Default",
                            onClick = {
                                viewModel.setTheme(AppearanceMode.SYSTEM_DEFAULT.key)
                                showAppearanceDialog.value = false
                            }
                        )
                    }
                }
            }
        )
    }
}
