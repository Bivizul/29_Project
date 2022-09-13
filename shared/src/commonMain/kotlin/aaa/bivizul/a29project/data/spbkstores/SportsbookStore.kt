package aaa.bivizul.a29project.data.spbkstores

import aaa.bivizul.a29project.data.model.Sportsbook
import aaa.bivizul.a29project.data.spbkkutil.uiDispatcher
import aaa.bivizul.a29project.data.spbknet.SpbkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SportsbookStore {

    private val spbkApi = SpbkApi()

    val job = SupervisorJob()
    val scope = CoroutineScope(uiDispatcher + job)

//        private val _sportsbook = MutableSharedFlow<List<Sportsbook>>()
//    val sportsbook: SharedFlow<List<Sportsbook>> = _sportsbook.asSharedFlow()
    private val _sportsbook = MutableStateFlow<List<Sportsbook>?>(null)
    val sportsbook: StateFlow<List<Sportsbook>?> = _sportsbook.asStateFlow()

    init {
        getSportsbook()
    }

    fun getSportsbook() {
        scope.launch {
            val response = spbkApi.getSportsbook()
            _sportsbook.emit(response)
        }
    }

}