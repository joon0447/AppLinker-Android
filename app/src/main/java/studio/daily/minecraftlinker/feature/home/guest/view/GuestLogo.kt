package studio.daily.minecraftlinker.feature.home.guest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.daily.minecraftlinker.R
import studio.daily.minecraftlinker.ui.text.GuestText

@Composable
fun GuestLogo() {
    Image(
        painter = painterResource(R.drawable.home_logo),
        contentDescription = "home-logo",
        modifier = Modifier.size(300.dp)
    )
    Spacer(Modifier.height(10.dp))
    Text(
        text = GuestText.TITLE,
        fontSize = 28.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(10.dp))
    Text(
        text = GuestText.SUB_TITLE,
        fontSize = 16.sp,
        color = Color.Gray,
        fontWeight = FontWeight.Normal
    )
}