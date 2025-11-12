package studio.daily.minecraftlinker.feature.home.login.repository

import studio.daily.minecraftlinker.core.network.server.RetrofitProvider
import studio.daily.minecraftlinker.core.network.server.ServerResponse

class ServerRepository {
    suspend fun fetchPlayers(): ServerResponse {
        return RetrofitProvider.serverAPI.getPlayers()
    }
}