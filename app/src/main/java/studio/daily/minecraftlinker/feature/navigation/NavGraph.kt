package studio.daily.minecraftlinker.feature.navigation

import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import studio.daily.minecraftlinker.feature.auth.view.AuthScreen
import studio.daily.minecraftlinker.feature.home.guest.view.GuestScreen
import studio.daily.minecraftlinker.feature.home.member.view.HomeScreen
import studio.daily.minecraftlinker.feature.start.view.LoadingScreen
import studio.daily.minecraftlinker.feature.start.viewmodel.MainViewModel
import studio.daily.minecraftlinker.feature.start.viewmodel.MainViewModelFactory

object Routes {
    const val HOME = "home"
    const val GUEST = "guest"
    const val AUTH = "auth"
    const val SPLASH = "splash"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext))
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
    ) {

        composable(Routes.SPLASH) {
            LoadingScreen(
                viewModel = viewModel,
                onResolved = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.GUEST) {
            GuestScreen(
                onNavigateToAuth = {
                    navController.navigate(Routes.AUTH)
                }
            )
        }

        composable(Routes.AUTH) {
            AuthScreen(
                onBack = {
                    val popped = navController.popBackStack()
                    if(!popped) {
                        navController.navigate(Routes.SPLASH) {
                            launchSingleTop = true
                        }
                    }
                },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.GUEST) {inclusive = true}
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}
