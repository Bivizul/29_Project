package aaa.bivizul.a29project.ui.navigation

import aaa.bivizul.a29project.ui.screen.DetailScreen
import aaa.bivizul.a29project.ui.screen.HomeScreen
import aaa.bivizul.a29project.ui.screen.SpbkhomeScreen
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
actual fun NavGraph() {

    val navHostController: NavHostController = rememberNavController()
    val startDestination: String = MainDestination.Spbkhome.route

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

//    val draftresRepository = DraftresRepository()

    NavHost(navController = navHostController, startDestination = startDestination) {

        composable(route = startDestination) {
            SpbkhomeScreen(
                navController = navHostController,
//                context = context,
//                activity = activity,
//                draftresRepository = draftresRepository
            )
        }

        composable(route = MainDestination.Home.route) {
            HomeScreen(
                navController = navHostController,
//                context = context,
//                activity = activity,
            )
            BackHandler() {
                activity.finish()
                System.exit(0)
            }
        }

        composable(route = MainDestination.Detail.route) {
            DetailScreen(
//                navController = navHostController,
//                context = context,
//                activity = activity,
//                draftresRepository = draftresRepository
            )
        }
    }
}
