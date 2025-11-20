package studio.daily.minecraftlinker.feature.home.member.repository

import android.net.http.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import studio.daily.minecraftlinker.core.network.mojang.MojangApi
import studio.daily.minecraftlinker.feature.home.member.model.MinecraftProfile

class ProfileRepository(
    private val mojangApi: MojangApi,
) {

    suspend fun fetchProfile(uuidRaw: String): MinecraftProfile = withContext(Dispatchers.IO) {
        val uuidNoHyphen = uuidRaw.replace("-", "").trim()

        val response = try {
            mojangApi.getProfile(uuidNoHyphen)
        } catch (e: HttpException) {
            throw IllegalStateException("플레이어 프로필을 찾을 수 없습니다. (uuid=$uuidNoHyphen)", e)
        }

        var skinUrl = buildAvatarUrl(uuidRaw)

        return@withContext MinecraftProfile(
            id = response.id,
            name = response.name,
            skinUrl = skinUrl
        )
    }

    private fun buildAvatarUrl(
        uuidNoHyphen: String,
        size: Int = 256,
        overlay: Boolean = true
    ): String {
        val base = "https://mc-heads.net/head/$uuidNoHyphen"
        val params = buildString {
            append("?size=$size")
            if (overlay) append("&overlay")
        }
        return base + params
    }
}