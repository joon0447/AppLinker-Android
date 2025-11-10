package studio.daily.minecarftlinker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val _authCode = MutableStateFlow("")
    val authCode: StateFlow<String> = _authCode

    private val _isCodeValid = MutableStateFlow(false)
    val isCodeValid: StateFlow<Boolean> = _isCodeValid

    fun onCodeChanged(code: String) {
        _authCode.value = code
        _isCodeValid.value = code.length == 6
    }

    fun connectToServer() {}
}