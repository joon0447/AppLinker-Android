package studio.daily.minecraftlinker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.daily.minecraftlinker.feature.home.guest.view.GuestScreen
import studio.daily.minecraftlinker.feature.home.login.view.HomeScreen
import studio.daily.minecraftlinker.feature.navigation.AppNavGraph
import studio.daily.minecraftlinker.feature.start.view.LoadingScreen
import studio.daily.minecraftlinker.feature.start.viewmodel.MainViewModel
import studio.daily.minecraftlinker.feature.start.viewmodel.MainViewModelFactory
import studio.daily.minecraftlinker.ui.theme.MinecarftLinkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinecarftLinkerTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}
