package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.data.model.Spbk
import aaa.bivizul.a29project.data.spbkkutil.*
import aaa.bivizul.a29project.data.spbkstores.SpbkStore
import aaa.bivizul.a29project.data.spbkstores.SportsbookStore
import aaa.bivizul.a29project.ui.navigation.MainDestination
import aaa.bivizul.a29project.ui.navigation.NavController
import aaa.bivizul.a29project.ui.navigation.navigate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun SpbkhomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    context: Any,
    activity: Any,
    spbkStore: SpbkStore,
    sportsbookStore: SportsbookStore
) {

    println("SpbkhomeScreen")

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
//            sportsbookStore.getSportsbook()
            navController.navigate(MainDestination.Between.route)
        } catch (e: Exception) {
            getSpbkdlg(context, activity)
        }
    }

}
