package studio.daily.minecraftlinker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import studio.daily.minecraftlinker.feature.home.guest.view.GuestScreen
import studio.daily.minecraftlinker.ui.theme.MinecarftLinkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinecarftLinkerTheme {
                GuestScreen()
            }
        }
    }
}
