package com.example.secretmessagesencrypter.precentation.screen.decryption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.secretmessagesencrypter.precentation.screen.EncryptionViewModel
import com.example.secretmessagesencrypter.utils.Constants

@Composable
fun DecryptionScreen(modifier: Modifier = Modifier, viewModel: EncryptionViewModel) {

    val decryptedText = viewModel.decryptedText.collectAsStateWithLifecycle()

    val key = rememberSaveable { mutableStateOf("") }
    val inputText = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.SpaceBetween,
//        contentAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = key.value,
                onValueChange = { key.value = it },
                placeholder = {
                    Text("Enter your key (optional)")
                },
                singleLine = true,
                shape = RoundedCornerShape(100)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputText.value,
                onValueChange = { inputText.value = it },
                placeholder = {
                    Text("Enter your encrypted text")
                },
                singleLine = true,
                shape = RoundedCornerShape(100)
            )
            Button(
                onClick = {
                    viewModel.decryptText(
                        inputText.value,
                        key.value.ifEmpty { Constants.DEFAULT_KEY }
                    )
                }
            ) {
                Text(text = "Decrypt Now")
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text(modifier = Modifier.padding(horizontal = 8.dp), text = decryptedText.value)
            }
        }
    }
}