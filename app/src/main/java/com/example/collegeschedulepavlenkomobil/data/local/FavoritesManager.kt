package com.example.collegeschedulepavlenkomobil.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("favorites")

class FavoritesManager(private val context: Context) {

    companion object {
        private val FAVORITE_GROUPS_KEY = stringSetPreferencesKey("favorite_groups")
    }

    val favoriteGroups: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITE_GROUPS_KEY] ?: emptySet()
        }

    suspend fun addFavorite(groupName: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITE_GROUPS_KEY] ?: emptySet()
            preferences[FAVORITE_GROUPS_KEY] = current + groupName
        }
    }

    suspend fun removeFavorite(groupName: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITE_GROUPS_KEY] ?: emptySet()
            preferences[FAVORITE_GROUPS_KEY] = current - groupName
        }
    }

    suspend fun toggleFavorite(groupName: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITE_GROUPS_KEY] ?: emptySet()
            preferences[FAVORITE_GROUPS_KEY] = if (groupName in current) {
                current - groupName
            } else {
                current + groupName
            }
        }
    }

    suspend fun isFavorite(groupName: String): Boolean {
        return favoriteGroups.first().contains(groupName)
    }
}