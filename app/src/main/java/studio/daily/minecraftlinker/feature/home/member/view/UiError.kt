package studio.daily.minecraftlinker.feature.home.member.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.daily.minecraftlinker.ui.text.MemberText

@Composable
fun UiError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = MemberText.ERROR_MESSAGE.format(message)
        )
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) {
            Text(
                text = MemberText.ERROR_BUTTON
            )
        }
    }
}