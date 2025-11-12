package studio.daily.minecraftlinker.core.network.server

import retrofit2.http.GET

interface ServerAPI {
    @GET("/players")
    suspend fun getPlayers(): ServerResponse
}