package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.data.model.Spbkvar
import aaa.bivizul.a29project.data.spbkkutil.sigSpbkoff
import aaa.bivizul.a29project.data.spbkstores.SpbkStore
import aaa.bivizul.a29project.ui.navigation.MainDestination
import aaa.bivizul.a29project.ui.navigation.NavController
import aaa.bivizul.a29project.ui.navigation.navigate
import aaa.bivizul.a29project.ui.spbkwidget.Spbkcircprogress
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun BetweenScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    spbkStore: SpbkStore,
//    sportsbookStore: SportsbookStore
) {

    println("BetweenScreen")
    val u = "https://web.telegram.org/k/"

    val getspbk by spbkStore.spbk.collectAsState()
//    val sportbook by sportsbookStore.sportsbook.collectAsState()

    if (getspbk != null) {
        getspbk?.getspbk?.let { getspbkstr ->
            println("BetweenScreen $getspbkstr")
            if (getspbkstr == Spbkvar.SVNO.sv) {
                navController.navigate(MainDestination.Home.route)
            } else if (getspbkstr == Spbkvar.SVNP.sv) {
                sigSpbkoff()
                navController.navigate(MainDestination.Home.route)
            } else {
//                    draftcc(context, it)
//                    screenDraftExit(activity)
                navController.navigate(MainDestination.Web.route + "/${u}")
            }
        }
    } else {
        Spbkcircprogress()
    }
}