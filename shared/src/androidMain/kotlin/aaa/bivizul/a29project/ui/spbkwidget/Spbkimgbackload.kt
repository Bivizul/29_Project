package aaa.bivizul.a29project.ui.spbkwidget

import aaa.bivizul.a29project.data.spbkkutil.Spbkcon
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import coil.compose.AsyncImage
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource

@Composable
actual fun Spbkimgbackload() {

    val orientation = LocalConfiguration.current.orientation
    val imageBack = when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> Spbkcon.BACK_V
        else -> Spbkcon.BACK_H
    }

        KamelImage(
            resource = lazyPainterResource(data = imageBack),
            contentDescription = "image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

}