package aaa.bivizul.a29project.data.spbkstores

import aaa.bivizul.a29project.data.model.Getspbk
import aaa.bivizul.a29project.data.model.Spbk
import aaa.bivizul.a29project.data.spbkkutil.uiDispatcher
import aaa.bivizul.a29project.data.spbknet.SpbkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SpbkStore {

    private val spbkApi = SpbkApi()

    val job = SupervisorJob()
    val scope = CoroutineScope(uiDispatcher + job)

    private val _spbk = MutableStateFlow<Getspbk?>(null)
    val spbk: StateFlow<Getspbk?> = _spbk.asStateFlow()

    fun getGetspbk(spbk: Spbk) {
        scope.launch {
            val response = spbkApi.getGetspbk(spbk)
            _spbk.emit(response)
        }
    }

}