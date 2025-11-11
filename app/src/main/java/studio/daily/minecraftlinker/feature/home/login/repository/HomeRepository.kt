package studio.daily.minecraftlinker.feature.home.login.repository

import android.net.http.HttpException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import studio.daily.minecraftlinker.core.network.MojangApi
import studio.daily.minecraftlinker.core.network.TexturesPayload
import studio.daily.minecraftlinker.feature.home.login.model.MinecraftProfile
import android.util.Base64

class HomeRepository(
    private val mojangApi: MojangApi,
) {

    suspend fun fetchProfile(uuidRaw: String): MinecraftProfile = withContext(Dispatchers.IO) {
        val uuidNoHyphen = uuidRaw.replace("-", "").trim()

        val response = try {
            mojangApi.getProfile(uuidNoHyphen)
        } catch (e: HttpException) {
            throw IllegalStateException("플레이어 프로필을 찾을 수 없습니다. (uuid=$uuidNoHyphen)", e)
        }

        var skinUrl = buildAvatarUrl(uuidNoHyphen)

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
        val base = "https://crafatar.com/avatars/$uuidNoHyphen"
        val params = buildString {
            append("?size=$size")
            if(overlay) append("&overlay")
        }
        return base+params
    }
}