package studio.daily.minecraftlinker.core.network.mojang

import retrofit2.http.GET
import retrofit2.http.Path

interface MojangApi {
    @GET("session/minecraft/profile/{uuid}")
    suspend fun getProfile(
        @Path("uuid") uuidWithoutHyphens: String
    ): MojangProfileResponse
}

data class MojangProfileResponse(
    val id: String,
    val name: String,
    val properties: List<MojangProperty>
)

data class MojangProperty(
    val name: String,
    val value: String,
    val signature: String? = null
)