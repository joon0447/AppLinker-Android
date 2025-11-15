package studio.daily.minecraftlinker.feature.home.login.repository

import studio.daily.minecraftlinker.core.network.server.CheckInRequest
import studio.daily.minecraftlinker.core.network.server.CheckInResponse
import studio.daily.minecraftlinker.core.network.server.ServerAPI
import studio.daily.minecraftlinker.core.network.server.ServerRetrofitProvider
import studio.daily.minecraftlinker.core.network.server.ServerResponse
import studio.daily.minecraftlinker.core.network.server.ServerRetrofitProvider.serverAPI

class ServerRepository(
    private val api: ServerAPI = serverAPI
) {
    suspend fun fetchPlayers(): ServerResponse {
        return api.getPlayers()
    }

    suspend fun checkIn(uuid: String): CheckInResponse {
        return api.checkIn(uuid)
    }
}