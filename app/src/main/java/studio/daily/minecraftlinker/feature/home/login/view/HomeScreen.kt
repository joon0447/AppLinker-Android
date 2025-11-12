package studio.daily.minecraftlinker.feature.home.login.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.feature.home.login.model.MinecraftProfile
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeUiState
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeViewModel
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeViewModelFactory
import studio.daily.minecraftlinker.feature.home.login.viewmodel.ServerViewModel

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(UuidStore(context)))
    val state by viewModel.uiState.collectAsState()

    val serverViewModel: ServerViewModel = viewModel()
    val serverResponse by serverViewModel.serverResponse.collectAsState()

    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    LaunchedEffect(Unit) {
        serverViewModel.loadPlayers()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val s = state) {
            HomeUiState.Idle,
            HomeUiState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            is HomeUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "로딩 실패: ${s.message}"
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { viewModel.refresh() }) {
                        Text(
                            text = "다시 시도"
                        )
                    }
                }
            }

            is HomeUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Header(
                        profile = s.profile,
                        statusBarHeight = statusBarHeight,
                        context = LocalContext.current,
                        onRefresh = {
                            viewModel.refresh()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    profile: MinecraftProfile,
    statusBarHeight: Dp,
    context: Context,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF8B5CF6),
                        Color(0xFF06B6D4)
                    )
                )
            )
            .padding(top = statusBarHeight, bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                if (profile.skinUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(profile.skinUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "플레이어 스킨",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("스킨이 없습니다.")
                }
                Spacer(Modifier.width(16.dp))
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))
            Button(onClick = onRefresh) {
                Text("새로고침")
            }
        }
    }
}