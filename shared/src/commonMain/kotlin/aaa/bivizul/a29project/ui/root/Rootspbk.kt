package aaa.bivizul.a29project.ui.root

import aaa.bivizul.a29project.ui.navigation.NavGraph
import aaa.bivizul.a29project.ui.spbkwidget.Spbkimgbackload
import aaa.bivizul.a29project.ui.theme.A29ProjectTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Rootspbk(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        A29ProjectTheme {
            Spbkimgbackload()
            NavGraph()

        }
    }
}