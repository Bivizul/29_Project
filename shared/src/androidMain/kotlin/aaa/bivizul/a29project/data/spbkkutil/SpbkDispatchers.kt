package aaa.bivizul.a29project.data.spbkkutil

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext = Dispatchers.Main
actual val ioDispatcher: CoroutineContext = Dispatchers.IO