package studio.daily.minecraftlinker.feature.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import studio.daily.minecraftlinker.core.datastore.UuidStore

class MainViewModel(
    private val uuidStore: UuidStore
) : ViewModel() {
    enum class StartDestination { Loading, Guest, Home }

    val startDestination: StateFlow<StartDestination> = uuidStore.uuidFlow
        .map { uuid ->
            if (uuid.isNullOrBlank()) StartDestination.Guest else StartDestination.Home
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StartDestination.Loading
        )
}