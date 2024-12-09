package com.example.secretmessagesencrypter.precentation.screen.drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secretmessagesencrypter.utils.Constants
import com.example.secretmessagesencrypter.utils.Utils

@Composable
fun DrawerScreen(
    modifier: Modifier = Modifier,
    encryptionClicked: () -> Unit,
    decryptionClicked: () -> Unit,
    settingsClicked: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.1f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(10.dp)
            )
            .graphicsLayer {
                shape = RoundedCornerShape(10.dp)
                clip = true
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
                    .padding(horizontal = 12.dp)
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                )
                Text(
                    text = "Enjoy Your Secrets",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

//                AnimatedVisibility(
//                    modifier = Modifier.fillMaxWidth(),
//                    visible = username.collectAsStateWithLifecycle().value.isNotEmpty()) {
//                    Text(
//                        text = "Hy \uD83D\uDC4B\uD83C\uDFFB ${username.collectAsStateWithLifecycle().value} \nI'm Sultan \uD83D\uDE05.",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 24.sp,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }

                Spacer(modifier = Modifier.fillMaxHeight(0.05f))

                ModifiedTextButton(
                    text = "Encryption",
                    onClick = encryptionClicked
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 18.dp)
                )
                ModifiedTextButton(
                    text = "Decryption",
                    onClick = decryptionClicked
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 18.dp)
                )
                ModifiedTextButton(
                    text = "Settings",
                    onClick = settingsClicked
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 18.dp)
                )
                ModifiedTextButton(
                    text = "Developer Contact",
                    onClick = { Utils.openWhatsApp(context, Constants.DEVELOPER_CONTACT) }
                )

            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = "Powered by Sultan VU",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }

}

@Composable
fun ModifiedTextButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 6.dp)
            .graphicsLayer { clip = true; shape = RoundedCornerShape(8.dp) }
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp),
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}