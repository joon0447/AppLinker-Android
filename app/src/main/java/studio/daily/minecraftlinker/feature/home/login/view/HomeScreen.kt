package studio.daily.minecraftlinker.feature.home.login.view

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.core.network.mojang.MojangApi
import studio.daily.minecraftlinker.core.network.mojang.RetrofitProvider
import studio.daily.minecraftlinker.core.network.server.ServerResponse
import studio.daily.minecraftlinker.feature.home.login.model.MinecraftProfile
import studio.daily.minecraftlinker.feature.home.login.repository.HomeRepository
import studio.daily.minecraftlinker.feature.home.login.viewmodel.FriendViewModel
import studio.daily.minecraftlinker.feature.home.login.viewmodel.FriendViewModelFactory
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeUiState
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeViewModel
import studio.daily.minecraftlinker.feature.home.login.viewmodel.HomeViewModelFactory
import studio.daily.minecraftlinker.feature.home.login.viewmodel.ServerViewModel

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(UuidStore(context)))
    val state by viewModel.uiState.collectAsState()

    val serverViewModel: ServerViewModel = viewModel()
    val serverResponse by serverViewModel.serverResponse.collectAsState()

    val friendViewModel: FriendViewModel = viewModel(
        factory = FriendViewModelFactory(
            uuidStore = UuidStore(context),
            repository = HomeRepository(RetrofitProvider.mojangApi)
        )
    )
    val friends by friendViewModel.friendUuids.collectAsState()
    val friendProfiles by friendViewModel.friendProfiles.collectAsState()
    val isLoading by friendViewModel.isLoading.collectAsState()
    val error by friendViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        serverViewModel.loadPlayers()
        friendViewModel.loadFriends()
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
                    ServerInfo(
                        playersCount = serverResponse!!.count
                    )
                    FriendsList(
                        friends = friends,
                        profiles = friendProfiles
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

@Composable
private fun ServerInfo(
    playersCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFCCCCCC),
                    shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "현재 서버 접속 인원",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = playersCount.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
private fun FriendsList(
    friends: List<String>,
    profiles: List<MinecraftProfile>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text("친구 목록", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        if (profiles.isEmpty()) {
            Text("친구가 없습니다.")
        } else {
            LazyColumn {
                items(profiles) { profile ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFF8FAFC),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(24.dp)
                    ) {
                        AsyncImage(
                            model = profile.skinUrl,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(12.dp))
                        Column() {
                            Text(profile.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        }

                    }
                }
            }
        }
    }

}
