package studio.daily.minecraftlinker.feature.home.guest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.daily.minecraftlinker.R
import studio.daily.minecraftlinker.ui.text.GuestText
import studio.daily.minecraftlinker.ui.theme.Green20

@Composable
fun ConnectButton(
    onNavigateToAuth: () -> Unit
) {
    Button(
        onClick = {
            onNavigateToAuth()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Green20),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 48.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.home_icon),
                contentDescription = "home",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = GuestText.CONNECT_BUTTON,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}