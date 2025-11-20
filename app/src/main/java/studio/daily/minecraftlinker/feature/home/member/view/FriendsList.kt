package studio.daily.minecraftlinker.feature.home.member.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import studio.daily.minecraftlinker.feature.home.member.model.MinecraftProfile
import studio.daily.minecraftlinker.ui.text.MemberText

@Composable
fun FriendsList(
    profiles: List<MinecraftProfile>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            MemberText.FRIEND_LIST_TITLE,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        if (profiles.isEmpty()) {
            Text(MemberText.NO_FRIEND)
        } else {
            LazyColumn {
                items(profiles) { profile ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFF8FAFC),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(24.dp)
                    ) {
                        AsyncImage(
                            model = profile.skinUrl,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(profile.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                            Text(
                                text = if (profile.isOnline) MemberText.ONLINE else MemberText.OFFLINE,
                                fontWeight = FontWeight.Normal,
                                color = if (profile.isOnline) Color.Green else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}