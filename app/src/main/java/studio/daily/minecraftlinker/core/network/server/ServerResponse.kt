package studio.daily.minecraftlinker.core.network.server

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(
    val count: Int,
    val uuids: List<String>
)