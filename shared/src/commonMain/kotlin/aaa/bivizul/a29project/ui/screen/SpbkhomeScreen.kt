package aaa.bivizul.a29project.ui.screen

import aaa.bivizul.a29project.ui.navigation.MainDestination
import aaa.bivizul.a29project.ui.navigation.NavController
import aaa.bivizul.a29project.ui.navigation.navigate
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SpbkhomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {


    Column(modifier) {
        Text(text = "SpbkhomeScreen")
        Button(
            onClick = { navController.navigate(MainDestination.Home.route) },
        ){
            Text("Home")
        }
    }
}
