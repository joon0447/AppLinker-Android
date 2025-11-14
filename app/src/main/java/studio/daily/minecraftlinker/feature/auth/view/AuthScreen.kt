package studio.daily.minecraftlinker.feature.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import studio.daily.minecraftlinker.R
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.feature.auth.viewmodel.AuthViewModel
import studio.daily.minecraftlinker.feature.auth.viewmodel.AuthViewModelFactory
import studio.daily.minecraftlinker.ui.theme.Blue20
import studio.daily.minecraftlinker.ui.theme.Brown60
import studio.daily.minecraftlinker.ui.theme.Gray80
import studio.daily.minecraftlinker.ui.theme.Orange20
import studio.daily.minecraftlinker.ui.theme.Yellow20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val context = LocalContext.current
    val uuidStore = remember { UuidStore(context) }
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(uuidStore)
    )

    val code by viewModel.authCode.collectAsState()
    val isValid by viewModel.isCodeValid.collectAsState()
    val uuid by viewModel.uuid.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(uuid) {
        if(!uuid.isNullOrBlank()) {
            onLoginSuccess()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "뒤로"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        text = "서버 연결",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Box(
                Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF7C4DFF), Color(0xFF00E5FF))
                        )
                    )
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.server),
                    contentDescription = "server",
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Box(
                Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "서버에 연결하세요",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "서버 연결 코드를 입력하면\n" +
                                "마인크래프트 서버와 연동할 수 있습니다.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                Column() {
                    Text(
                        text = "서버에 연결하세요",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "서버에서 받은 6자리 코드를 입력해주세요.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    VerificationCodeInput(
                        onCodeComplete = {viewModel.onCodeChanged(it)}
                    )
                    HelpContainer()
                    Spacer(Modifier.height(32.dp))
                    ConnectButton(enabled = isValid,
                        onClick = {
                            viewModel.connectToServer()
                        })
                }
            }
        }
    }
}

@Composable
private fun VerificationCodeInput(
    codeLength: Int = 6,
    onCodeComplete: (String) -> Unit = {}
) {
    val focusRequesters = List(codeLength) { FocusRequester() }
    val code = remember { mutableStateListOf(*Array(codeLength) { "" }) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 22.dp)
    ) {
        repeat(codeLength) { index ->
            OutlinedTextField(
                value = code[index],
                onValueChange = { value ->
                    val upperCaseValue = value.uppercase()
                    if (upperCaseValue.length <= 1) {
                        code[index] = upperCaseValue

                        if (upperCaseValue.isNotEmpty() && index < codeLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (code.joinToString("").length == codeLength && !code.contains("")) {
                            keyboardController?.hide()
                            onCodeComplete(code.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown &&
                            event.key == Key.Backspace &&
                            code[index].isEmpty() &&
                            index > 0
                        ) {
                            code[index - 1] = ""
                            focusRequesters[index - 1].requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = if (index == codeLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
        }

    }
}

@Composable
private fun HelpContainer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Yellow20, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Orange20,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "알림"
                )
                Text(
                    text = "도움말",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Brown60
                )
            }
            Text(
                text = "- 코드는 24시간 후 만료됩니다.",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Brown60
            )
        }
    }
}

@Composable
private fun ConnectButton(
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Blue20 else Gray80,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = "서버 연결하기",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}