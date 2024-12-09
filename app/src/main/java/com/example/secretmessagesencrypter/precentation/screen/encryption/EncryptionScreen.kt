package com.example.secretmessagesencrypter.precentation.screen.encryption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.secretmessagesencrypter.R
import com.example.secretmessagesencrypter.precentation.screen.EncryptionViewModel
import com.example.secretmessagesencrypter.precentation.screen.settings.SettingsViewModel
import com.example.secretmessagesencrypter.utils.Constants
import com.example.secretmessagesencrypter.utils.Utils

@Composable
fun EncryptionApp(modifier: Modifier = Modifier, viewModel: EncryptionViewModel, settingsViewModel: SettingsViewModel) {

    val context = LocalContext.current

    var inputText by remember { mutableStateOf(TextFieldValue("")) }

    val messages by viewModel.messages.collectAsStateWithLifecycle()

    val encryptionKey = settingsViewModel.encryptionKey.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom // .spacedBy(16.dp)
    ) {
//        Text("Text Encryption/Decryption App", style = MaterialTheme.typography.displayMedium)

        LazyColumn(modifier = Modifier.weight(8f)) {
            items(messages) {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                    if (it.id != messages.first().id){
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().padding(24.dp)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Text(
                            modifier = Modifier.weight(9f),
                            text = "Encrypted Text: ${it.content} \n Key: ${it.key.ifEmpty { "Default" }}"
                        )
                        IconButton(
                            onClick = {
                                Utils.copyToClipboard(context = context, text = it.content)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_copy),
                                contentDescription = "copy"
                            )
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.BottomCenter){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text("Enter text to encrypt")
                },
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.encryptText(inputText.text, encryptionKey.value.ifEmpty { Constants.DEFAULT_KEY })
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = "send"
                        )
                    }
                },
                shape = RoundedCornerShape(100)
            )
        }


//        Button(
//            onClick = { viewModel.encryptText(inputText.text) },
//            modifier = Modifier
//        ) {
//            Text("Encrypt Text")
//        }

//        if (encryptedText.isNotEmpty()) {
//
//
//            Button(
//                onClick = { viewModel.decryptText(encryptedText) },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Decrypt Text")
//            }
//        }

//        if (decryptedText.isNotEmpty()) {
//            Text("Decrypted Text: $decryptedText")
//        }
    }
}
