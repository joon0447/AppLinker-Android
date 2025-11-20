package studio.daily.minecraftlinker.feature.home.member.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import studio.daily.minecraftlinker.feature.home.member.repository.RewardRepository

class RewardViewModel(
    private val repository: RewardRepository = RewardRepository()
) : ViewModel() {

    private val _canReceiveToday = MutableStateFlow<Boolean?>(null)
    val canReceiveToday: StateFlow<Boolean?> = _canReceiveToday

    fun loadRewardStatus(uuid: String) {
        viewModelScope.launch {
            try {
                val canReceive = repository.canReceivedRewardToday(uuid)
                _canReceiveToday.value = canReceive
            } catch (e: Exception) {
                _canReceiveToday.value = true
            }
        }
    }

    fun setReceivedReward() {
        _canReceiveToday.value = false
    }
}