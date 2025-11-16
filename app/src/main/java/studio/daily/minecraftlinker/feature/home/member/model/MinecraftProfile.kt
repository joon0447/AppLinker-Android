package studio.daily.minecraftlinker.feature.home.member.model

data class MinecraftProfile (
    val id: String,
    val name: String,
    val skinUrl: String?,
    val isOnline: Boolean = false
)