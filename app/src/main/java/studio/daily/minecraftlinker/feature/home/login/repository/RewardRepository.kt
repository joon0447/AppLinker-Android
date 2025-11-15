package studio.daily.minecraftlinker.feature.home.login.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RewardRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val zoneId: ZoneId = ZoneId.systemDefault(),
) {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    suspend fun canReceivedRewardToday(uuid: String): Boolean {
        val snapshot = db.collection("rewards")
            .document(uuid)
            .get()
            .await()

        val rewardAt = snapshot.getString("rewardedAt")
        return checkDateIsSame(rewardAt)
    }

    private fun checkDateIsSame(rewardedAt: String?): Boolean {
        if(rewardedAt.isNullOrBlank()) return true
        return try{
            val lastDate = LocalDate.parse(rewardedAt, formatter)
            val today = LocalDate.now(zoneId)
            lastDate != today
        } catch (e: DateTimeParseException) {
            true
        }
    }
}