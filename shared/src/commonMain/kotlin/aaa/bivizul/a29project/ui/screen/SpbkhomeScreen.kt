package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.data.model.Spbk
import aaa.bivizul.a29project.data.spbkkutil.*
import aaa.bivizul.a29project.data.spbkstores.SpbkStore
import aaa.bivizul.a29project.ui.navigation.MainDestination
import aaa.bivizul.a29project.ui.navigation.NavController
import aaa.bivizul.a29project.ui.navigation.navigate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SpbkhomeScreen(
    navController: NavController,
    context: Any,
    activity: Any,
    spbkStore: SpbkStore,
) {

    LaunchedEffect(Unit) {
        try {
            spbkStore.getGetspbk(
                Spbk(
                    getSpbkmm(),
                    getSpbksim(context),
                    getSpbkid(context),
                    getSpbkl(),
                    getSpbkt()
                )
            )
            navController.navigate(MainDestination.Between.route)
        } catch (e: Exception) {
            getSpbkdlg(context, activity)
        }
    }

}
