package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.data.spbkkutil.Spbkcon
import aaa.bivizul.a29project.data.spbkstores.SportsbookStore
import aaa.bivizul.a29project.ui.navigation.MainDestination
import aaa.bivizul.a29project.ui.navigation.NavController
import aaa.bivizul.a29project.ui.navigation.navigate
import aaa.bivizul.a29project.ui.spbkwidget.Spbkcircprogress
import aaa.bivizul.a29project.ui.theme.Typography
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    sportsbookStore: SportsbookStore,
) {

    val sportbookList by sportsbookStore.sportsbook.collectAsState()

    if (sportbookList != null) {
        sportbookList?.let { list ->
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Spbkcon.NAME_APP,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = Typography.h3
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    items(list) { sportsbook ->
                        Card(
                            onClick = { navController.navigate(MainDestination.Detail.route + "/${sportsbook.id}") },
                            modifier = Modifier
                                .padding(4.dp)
                                .height(50.dp),
                            elevation = 8.dp,
                            backgroundColor = MaterialTheme.colors.primary
                        ) {
                            Text(
                                text = sportsbook.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    } else {
        Spbkcircprogress()
    }
}