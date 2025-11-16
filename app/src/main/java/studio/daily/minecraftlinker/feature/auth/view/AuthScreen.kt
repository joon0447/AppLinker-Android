package studio.daily.minecraftlinker.feature.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.feature.auth.viewmodel.AuthViewModel
import studio.daily.minecraftlinker.feature.auth.viewmodel.AuthViewModelFactory

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
    val isValid by viewModel.isCodeValid.collectAsState()
    val uuid by viewModel.uuid.collectAsState()

    LaunchedEffect(uuid) {
        if(!uuid.isNullOrBlank()) {
            onLoginSuccess()
        }
    }

    Scaffold(
        topBar = {
            AuthAppBar(onBack)
        },
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            AuthHeader()
            CodeInputField(
                onCodeComplete = {
                    viewModel.onCodeChanged(it)
                }
            )
            Spacer(Modifier.height(24.dp))
            ConnectButton(
                enabled = isValid,
                onClick = {
                    viewModel.connectToServer()
                }
            )
        }
    }
}