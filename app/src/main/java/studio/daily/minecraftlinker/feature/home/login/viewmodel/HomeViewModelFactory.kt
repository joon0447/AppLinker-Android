package studio.daily.minecraftlinker.feature.home.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import studio.daily.minecraftlinker.core.datastore.UuidStore
import studio.daily.minecraftlinker.core.network.mojang.RetrofitProvider
import studio.daily.minecraftlinker.feature.home.login.repository.HomeRepository

class HomeViewModelFactory(
    private val uuidStore: UuidStore
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(HomeViewModel::class.java))
        val repo = HomeRepository(
            mojangApi = RetrofitProvider.mojangApi
        )
        return HomeViewModel(uuidStore, repo) as T
    }
}