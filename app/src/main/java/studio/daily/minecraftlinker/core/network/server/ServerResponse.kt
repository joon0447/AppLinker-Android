package studio.daily.minecraftlinker.core.network.server

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(
    val count: Int,
    val uuids: List<String>
)

@Serializable
data class CheckInRequest(
    val uuid: String
)

@Serializable
data class CheckInResponse(
    val success: Boolean,
    val message: String? = null
)