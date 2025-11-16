package studio.daily.minecraftlinker.feature.home.member.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import studio.daily.minecraftlinker.R
import studio.daily.minecraftlinker.feature.home.member.model.MinecraftProfile

@Composable
fun Header(
    profile: MinecraftProfile,
    statusBarHeight: Dp,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = statusBarHeight + 25.dp)
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (profile.skinUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(profile.skinUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "플레이어 스킨",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("스킨이 없습니다.")
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = profile.name,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(id = R.drawable.material_symbols_logout),
            contentDescription = "로그아웃",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    // TODO 로그아웃 함수 구현하기
                }
        )
    }
}