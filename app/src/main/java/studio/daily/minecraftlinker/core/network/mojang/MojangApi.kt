package studio.daily.minecraftlinker.core.network.mojang

import com.google.gson.annotations.SerializedName
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

data class TexturesPayload(
    val timestamp: Long? = null,
    val profileId: String? = null,
    val profileName: String? = null,
    val textures: TexturesMap? = null
)

data class TexturesMap(
    @SerializedName("SKIN") val skin: SkinInfo? = null,
    @SerializedName("CAPE") val cape: CapeInfo? = null
)

data class SkinInfo(
    val url: String,
    val metadata: Map<String, Any>? = null
)

data class CapeInfo(
    val url: String
)