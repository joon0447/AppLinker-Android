package studio.daily.minecraftlinker.feature.home.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import studio.daily.minecraftlinker.core.network.server.ServerResponse
import studio.daily.minecraftlinker.feature.home.login.repository.ServerRepository

class ServerViewModel: ViewModel() {

    private val repository = ServerRepository()

    private val _serverResponse = MutableStateFlow<ServerResponse?>(null)
    val serverResponse: StateFlow<ServerResponse?> = _serverResponse

    fun loadPlayers() {
        viewModelScope.launch {
            try{
                val response = repository.fetchPlayers()
                _serverResponse.value = response
            } catch (e: Exception) {
                println("API 에러 : ${e.message}")
            }
        }
    }

    fun checkIn(uuid: String) {
        viewModelScope.launch {
            try{
                val response = repository.checkIn(uuid)
            }catch(e: Exception) {
                println("API 에러 : ${e.message}")
            }
        }
    }
}