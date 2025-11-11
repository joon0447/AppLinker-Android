package studio.daily.minecraftlinker.feature.home.login.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeUiState
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeViewModel
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeViewModelFactory

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(UuidStore(context)))
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(val s = state) {
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
                    Button(onClick = {viewModel.refresh()}) {
                        Text(
                            text = "다시 시도"
                        )
                    }
                }
            }

            is HomeUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = s.profile.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))

                    if (s.profile.skinUrl != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(s.profile.skinUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "플레이어 스킨",
                            modifier = Modifier.size(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("스킨이 설정되어 있지 않아요. 🤔")
                    }

                    Spacer(Modifier.height(24.dp))
                    Button(onClick = { viewModel.refresh() }) {
                        Text("새로고침")
                    }
                }
            }
        }
    }
}