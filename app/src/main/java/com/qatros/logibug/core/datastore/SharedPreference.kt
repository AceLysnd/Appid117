package com.qatros.logibug.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SharedPreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getState(): Flow<SessionModel> {
        return dataStore.data.map {
            SessionModel(
                it[TOKEN] ?: "",
                it[REFRESH_TOKEN] ?: "",
                it[IS_PASS_ONBOARDING]?:false,
                it[EXP] ?: 0,
                it[EMAIL] ?: "",
                it[ID] ?: 0,
                it[NAME] ?: "",
                it[IS_LOGIN]?: false,
                it[IMG_URL]?:""
            )
        }
    }

    suspend fun saveState(
        token: String,
        refreshToken: String,
        exp: Int,
        email: String,
        id: Int,
        name: String,
        imgUrl: String?
    ) {
        dataStore.edit {
            it[TOKEN] = token
            it[REFRESH_TOKEN] = refreshToken
            it[IS_PASS_ONBOARDING]= true
            it[EXP] = exp
            it[EMAIL] = email
            it[ID] = id
            it[NAME] = name
            it[IS_LOGIN] = true
            it[IMG_URL]= imgUrl?:""
        }
    }

    suspend fun saveOnboardingState(){
        dataStore.edit {
            it[IS_PASS_ONBOARDING]= true
        }
    }

    suspend fun saveLoginState(){
        dataStore.edit{
            it[IS_LOGIN] = true
        }
    }

    suspend fun logout(){
        dataStore.edit {
            it.remove(TOKEN)
            it.remove(REFRESH_TOKEN)
            it.remove(EXP)
            it.remove(EMAIL)
            it.remove(ID)
            it.remove(NAME)
            it.remove(IS_LOGIN)
            it.remove(IMG_URL)
        }
    }


    companion object {
        private val IS_LOGIN = booleanPreferencesKey("is_login")
        private val IS_PASS_ONBOARDING = booleanPreferencesKey("is_pass_onboarding")
        private val TOKEN = stringPreferencesKey("token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val EXP = intPreferencesKey("exp")
        private val EMAIL = stringPreferencesKey("email")
        private val ID = intPreferencesKey("com")
        private val NAME = stringPreferencesKey("name")
        private val IMG_URL = stringPreferencesKey("imgUrl")
    }
}