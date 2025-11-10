package studio.daily.minecraftlinker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.daily.minecraftlinker.core.datastore.UuidStore

class AuthViewModelFactory(
    private val uuidStore: UuidStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(AuthViewModel::class.java)) {
           @Suppress("UNCHECKED_CAST")
           return AuthViewModel(uuidStore) as T
       }
        throw IllegalArgumentException("뷰 모델이 잘못되었습니다.")
    }
}