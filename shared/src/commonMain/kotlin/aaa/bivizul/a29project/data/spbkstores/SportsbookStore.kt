package aaa.bivizul.a29project.data.spbkstores

import aaa.bivizul.a29project.data.model.Sportsbook
import aaa.bivizul.a29project.data.spbkkutil.ioDispatcher
import aaa.bivizul.a29project.data.spbknet.SpbkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SportsbookStore {

    private val spbkApi = SpbkApi()

    val job = SupervisorJob()
    val scope = CoroutineScope(ioDispatcher + job)

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