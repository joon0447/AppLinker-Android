package studio.daily.minecraftlinker.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "uuid")
class UuidStore(private val context: Context) {

    suspend fun saveUUID(uuid: String) {
        context.dataStore.edit { preferences ->
            preferences[UUID] = uuid
        }
    }

    val uuidFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[UUID]
        }

    companion object {
        val UUID = stringPreferencesKey("uuid")
    }
}