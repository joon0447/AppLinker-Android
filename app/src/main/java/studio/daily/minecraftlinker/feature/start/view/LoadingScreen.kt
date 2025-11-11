package studio.daily.minecraftlinker.feature.start.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import studio.daily.minecraftlinker.feature.navigation.Routes
import studio.daily.minecraftlinker.feature.start.viewmodel.MainViewModel

@Composable
fun LoadingScreen(
    viewModel: MainViewModel,
    onResolved: (route: String) -> Unit
) {
    val startDest = viewModel.startDestination.collectAsStateWithLifecycle().value

    LaunchedEffect(startDest) {
        when(startDest) {
            MainViewModel.StartDestination.Guest -> onResolved(Routes.GUEST)
            MainViewModel.StartDestination.Home -> onResolved(Routes.HOME)
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}