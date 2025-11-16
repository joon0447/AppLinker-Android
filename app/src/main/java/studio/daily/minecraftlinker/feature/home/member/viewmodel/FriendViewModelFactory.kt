package studio.daily.minecraftlinker.feature.home.member.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.core.network.server.ServerAPI
import studio.daily.minecraftlinker.feature.home.member.repository.ProfileRepository

class FriendViewModelFactory(
    private val uuidStore: UuidStore,
    private val repository: ProfileRepository,
    private val serverAPI: ServerAPI
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FriendViewModel(uuidStore, repository, serverAPI) as T
    }
}