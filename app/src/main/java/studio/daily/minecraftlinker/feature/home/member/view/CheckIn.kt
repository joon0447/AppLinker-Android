package studio.daily.minecraftlinker.feature.home.member.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import studio.daily.minecraftlinker.R
import studio.daily.minecraftlinker.ui.text.MemberText
import studio.daily.minecraftlinker.ui.theme.Blue40
import studio.daily.minecraftlinker.ui.theme.Blue60
import studio.daily.minecraftlinker.ui.theme.DarkBlue80
import studio.daily.minecraftlinker.ui.theme.Green40

@Composable
fun CheckIn(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(MemberText.CHECK_IN_TITLE,
            style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = DarkBlue80
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Blue60, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.diamond_je3_be3),
                        contentDescription = "다이아몬드",
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = MemberText.CHECK_IN_REWARD_TITLE,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Text(
                    text = MemberText.CHECK_IN_REWARD,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Blue40,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        onClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green40,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(MemberText.CHECK_IN_REWARD_SUBTITLE)
                }
            }
        }
    }
}