package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.data.spbkstores.SportsbookStore
import aaa.bivizul.a29project.ui.spbkwidget.Spbkcircprogress
import aaa.bivizul.a29project.ui.theme.Typography
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    sportsbookStore: SportsbookStore,
    idElement: String
) {

    println("DetailScreen id : $idElement")

    val sportsbook =
        sportsbookStore.sportsbook.collectAsState().value?.firstOrNull { it.id == idElement.toInt() }
    val scrollState = rememberScrollState()

    if (sportsbook != null) {
        Column(
            modifier.padding(16.dp).fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = sportsbook.title,
                style = Typography.h3,
                textAlign = TextAlign.Center
            )
            Text(
                text = sportsbook.description,
                style = Typography.body1
            )
        }
    } else {
        Spbkcircprogress()
    }

}
