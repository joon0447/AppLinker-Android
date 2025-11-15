package studio.daily.minecraftlinker.feature.home.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.feature.home.login.model.MinecraftProfile
import studio.daily.minecraftlinker.feature.home.login.repository.HomeRepository


sealed class HomeUiState {
    data object Idle : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val profile: MinecraftProfile) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val uuidStore: UuidStore,
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    var uiState: StateFlow<HomeUiState> = _uiState

    private val _uuid = MutableStateFlow<String?>(null)
    val uuid: StateFlow<String?> = _uuid

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val uuid = uuidStore.uuidFlow.first()
                if (uuid.isNullOrBlank()) {
                    _uiState.value = HomeUiState.Error("UUID가 없습니다.")
                    return@launch
                }
                _uuid.value = uuid
                val profile = repository.fetchProfile(uuid)
                _uiState.value = HomeUiState.Success(profile)
            } catch (t: Throwable) {
                _uiState.value = HomeUiState.Error(t.message ?: "오류")
            }
        }
    }
}