package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.data.model.Spbkvar
import aaa.bivizul.a29project.data.spbkkutil.getWebActivity
import aaa.bivizul.a29project.data.spbkkutil.sigSpbkoff
import aaa.bivizul.a29project.data.spbkstores.SpbkStore
import aaa.bivizul.a29project.ui.navigation.MainDestination
import aaa.bivizul.a29project.ui.navigation.NavController
import aaa.bivizul.a29project.ui.navigation.navigate
import aaa.bivizul.a29project.ui.spbkwidget.Spbkcircprogress
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun BetweenScreen(
    navController: NavController,
    spbkStore: SpbkStore,
    activity: Any
) {

    val getspbk by spbkStore.spbk.collectAsState()

    if (getspbk != null) {
        getspbk?.getspbk?.let { getspbkstr ->
            if (getspbkstr == Spbkvar.SVNO.sv) {
                navController.navigate(MainDestination.Home.route)
            } else if (getspbkstr == Spbkvar.SVNP.sv) {
                sigSpbkoff()
                navController.navigate(MainDestination.Home.route)
            } else {
                getWebActivity(activity, getspbkstr)
            }
        }
    } else {
        Spbkcircprogress()
    }
}