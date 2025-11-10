package studio.daily.minecarftlinker.feature.home.guest.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import studio.daily.minecarftlinker.R
import studio.daily.minecarftlinker.ui.theme.Green20
import studio.daily.minecarftlinker.ui.theme.Orange20
import studio.daily.minecarftlinker.ui.theme.Purple40

enum class Menu (
    val title: String,
    val description: String,
    val iconRes: Int,
    val backgroundColor: Color
) {
    FRIEND(
        title = "친구",
        description = "친구들의 접속 정보를 확인할 수 있습니다.",
        iconRes = R.drawable.friend,
        backgroundColor = Purple40
    ),
    REWARD(
        title = "일일 보상",
        description = "매일 출석하고 보상을 받을 수 있습니다.",
        iconRes = R.drawable.gift,
        backgroundColor = Green20
    ),
    SERVER(
        title = "서버 정보 확인",
        description = "서버 접속 인원을 실시간으로 확인할 수 있습니다.",
        iconRes = R.drawable.server,
        backgroundColor = Orange20
    )
}