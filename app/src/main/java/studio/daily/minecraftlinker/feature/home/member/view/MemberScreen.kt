package studio.daily.minecraftlinker.feature.home.member.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.core.network.mojang.MojangRetrofitProvider
import studio.daily.minecraftlinker.core.network.server.ServerRetrofitProvider
import studio.daily.minecraftlinker.feature.home.member.repository.ProfileRepository
import studio.daily.minecraftlinker.feature.home.member.viewmodel.FriendViewModel
import studio.daily.minecraftlinker.feature.home.member.viewmodel.FriendViewModelFactory
import studio.daily.minecraftlinker.feature.home.member.viewmodel.HomeUiState
import studio.daily.minecraftlinker.feature.home.member.viewmodel.HomeViewModel
import studio.daily.minecraftlinker.feature.home.member.viewmodel.ProfileViewModelFactory
import studio.daily.minecraftlinker.feature.home.member.viewmodel.RewardViewModel
import studio.daily.minecraftlinker.feature.home.member.viewmodel.ServerViewModel

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val homeViewModel: HomeViewModel =
        viewModel(factory = ProfileViewModelFactory(UuidStore(context)))
    val state by homeViewModel.uiState.collectAsState()
    val uuid by homeViewModel.uuid.collectAsState()

    val serverViewModel: ServerViewModel = viewModel()
    val serverResponse by serverViewModel.serverResponse.collectAsState()

    val friendViewModel: FriendViewModel = viewModel(
        factory = FriendViewModelFactory(
            uuidStore = UuidStore(context),
            repository = ProfileRepository(MojangRetrofitProvider.mojangApi),
            serverAPI = ServerRetrofitProvider.serverAPI
        )
    )
    val friendProfiles by friendViewModel.friendProfiles.collectAsState()

    val rewardViewModel: RewardViewModel = viewModel()
    val canReceiveToday by rewardViewModel.canReceiveToday.collectAsState()

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
                UiError(
                    message = s.message,
                    onRetry = {homeViewModel.refresh()},
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is HomeUiState.Success -> {
                LaunchedEffect(uuid) {
                    uuid?.let { uuid ->
                        if (uuid.isNotBlank()) {
                            rewardViewModel.loadRewardStatus(uuid)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Header(
                        profile = s.profile,
                        statusBarHeight = statusBarHeight,
                        context = LocalContext.current
                    )
                    serverResponse?.let { response ->
                        ServerInfo(playersCount = response.count)
                    }
                    FriendsList(profiles = friendProfiles)
                    when (canReceiveToday) {
                        null -> CircularProgressIndicator()
                        true -> {
                            CheckIn(onClick = {
                                uuid?.let { uuid ->
                                    serverViewModel.checkIn(uuid)
                                    rewardViewModel.setReceivedReward()
                                }
                            })
                        }
                        false -> CompleteCheckIn()
                    }
                }
            }
        }
    }
}