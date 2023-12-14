package com.nsicyber.boilerplate.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userUid")
        private val LAST_USER = stringPreferencesKey("last_user")
    }

    val getLastUser: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LAST_USER] ?: ""
    }

    suspend fun saveLastUser(token: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_USER] = token
        }
    }
}