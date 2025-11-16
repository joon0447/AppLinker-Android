package studio.daily.minecraftlinker.feature.home.member.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.core.network.server.ServerAPI
import studio.daily.minecraftlinker.feature.home.member.model.MinecraftProfile
import studio.daily.minecraftlinker.feature.home.member.repository.ProfileRepository

class FriendViewModel(
    private val uuidStore: UuidStore,
    private val repository: ProfileRepository,
    private val serverAPI: ServerAPI
) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _friendUuids = MutableStateFlow<List<String>>(emptyList())
    val friendUuids: StateFlow<List<String>> = _friendUuids

    private val _friendProfiles = MutableStateFlow<List<MinecraftProfile>>(emptyList())
    val friendProfiles: StateFlow<List<MinecraftProfile>> = _friendProfiles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadFriends() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try{
                val uuid = uuidStore.uuidFlow.first()
                if(uuid.isNullOrBlank()) {
                    _error.value = "UUID가 없습니다."
                    _isLoading.value = false
                    return@launch
                }

                val snapshot = db.collection("friends")
                    .document(uuid)
                    .get()
                    .await()

                if(snapshot.exists()) {
                    val friends = snapshot.get("friends") as? List<String> ?: emptyList()
                    _friendUuids.value = friends

                    val serverResponse = serverAPI.getPlayers()
                    val onlineUuids = serverResponse.uuids.toSet()

                    val profiles = friends.mapNotNull { friendUuid ->
                        try{
                            val profile = repository.fetchProfile(friendUuid)
                            profile.copy(
                                isOnline = onlineUuids.contains(friendUuid)
                            )
                        }catch(e: Exception) {
                            Log.e("FriendViewModel", "프로필 가져오기 실패: $friendUuid", e)
                            null
                        }
                    }
                    _friendProfiles.value = profiles
                    Log.d("FriendViewModel", "친구 목록 : ${_friendUuids.value}")
                } else {
                    _error.value = "친구가 없습니다."
                }
            } catch(e: Exception) {
                Log.e("FriendViewModel", "친구 로딩 실패", e)
                _error.value = "친구 로딩 중 오류가 발생했습니다."
            }finally {
                _isLoading.value = false
            }
        }
    }

}