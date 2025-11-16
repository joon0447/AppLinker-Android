package studio.daily.minecraftlinker.feature.home.guest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.daily.minecraftlinker.R
import studio.daily.minecraftlinker.feature.home.guest.model.Menu
import studio.daily.minecraftlinker.ui.theme.Blue20
import studio.daily.minecraftlinker.ui.theme.Green20


@Composable
fun GuestScreen(
    onNavigateToAuth: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Image(
                painter = painterResource(R.drawable.home_logo),
                contentDescription = "home-logo",
                modifier = Modifier.size(300.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "환영합니다!",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "서버와 연결하여 다양한 소식을 실시간으로 받아보세요!",
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(50.dp))
            Button(
                onClick = {

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
                        text = "서버와 연결하기",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}