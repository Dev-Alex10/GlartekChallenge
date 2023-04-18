package com.example.glartekchallenge.data.userpreferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(UserPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserPreferences(preferences.favoriteMovies)
        }

    suspend fun updateIsFavoriteMovieId(movieId: String, isFavorite: Boolean) {
        try {
            dataStore.updateData {
                if (isFavorite) {
                    it.copy(favoriteMovies = it.favoriteMovies + movieId)
                } else {
                    it.copy(favoriteMovies = it.favoriteMovies - movieId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
