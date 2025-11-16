package studio.daily.minecraftlinker.feature.home.member.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.core.network.mojang.MojangRetrofitProvider
import studio.daily.minecraftlinker.feature.home.member.repository.ProfileRepository

class ProfileViewModelFactory(
    private val uuidStore: UuidStore
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(HomeViewModel::class.java))
        val repo = ProfileRepository(
            mojangApi = MojangRetrofitProvider.mojangApi
        )
        return HomeViewModel(uuidStore, repo) as T
    }
}