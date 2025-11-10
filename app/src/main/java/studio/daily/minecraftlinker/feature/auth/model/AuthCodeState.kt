package studio.daily.minecraftlinker.feature.auth.model

data class AuthCodeState(
    val code: String = "",
    val isValid: Boolean = false
)