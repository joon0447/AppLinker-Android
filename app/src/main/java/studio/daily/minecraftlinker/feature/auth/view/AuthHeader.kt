package studio.daily.minecraftlinker.feature.auth.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.daily.minecraftlinker.ui.text.AuthText
import studio.daily.minecraftlinker.ui.theme.Gray60

@Composable
fun AuthHeader() {
    Column(
        modifier = Modifier
            .padding(30.dp)
    ) {
        Text(
            text = AuthText.TITLE,
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = AuthText.SUB_TITLE,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = AuthText.EXPLANATION,
            fontSize = 13.sp,
            color = Gray60,
            fontWeight = FontWeight.SemiBold
        )
    }
}