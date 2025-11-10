package studio.daily.minecraftlinker.feature.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import studio.daily.minecraftlinker.core.datastore.UuidStore

class AuthViewModel(private val uuidStore: UuidStore) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _uuid = MutableStateFlow<String?>(null)
    val uuid: StateFlow<String?> = _uuid
    private val _authCode = MutableStateFlow("")
    val authCode: StateFlow<String> = _authCode

    private val _isCodeValid = MutableStateFlow(false)
    val isCodeValid: StateFlow<Boolean> = _isCodeValid

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onCodeChanged(code: String) {
        _authCode.value = code
        _isCodeValid.value = code.length == 6
    }

    fun connectToServer() {
        val code = _authCode.value

        viewModelScope.launch {
            try{
                val snapshot = db.collection("linkCodes")
                    .whereEqualTo("code", code)
                    .limit(1)
                    .get()
                    .await()

                if(!snapshot.isEmpty) {
                    val document = snapshot.documents.first()

                    val uuid = document.id
                    _uuid.value = uuid
                    uuidStore.saveUUID(uuid)
                    Log.d("AuthViewModel", "uuid : $uuid")
                } else {
                    _error.value = "해당 코드를 찾을 수 없습니다."
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "오류", e)
            }
        }
    }
}