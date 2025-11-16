package studio.daily.minecraftlinker.core.network.server

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerAPI {
    @GET("/players")
    suspend fun getPlayers(): ServerResponse

    @POST("/check/{uuid}")
    suspend fun checkIn(
        @Path("uuid") uuid: String
    ): CheckInResponse
}