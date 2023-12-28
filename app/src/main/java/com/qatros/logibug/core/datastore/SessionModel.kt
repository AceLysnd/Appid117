package com.qatros.logibug.core.datastore

data class SessionModel(
    val token: String,
    val refreshToken: String,
    val isPassOnboarding: Boolean,
    val exp: Int,
    val email: String,
    val idUser: Int,
    val name: String,
    val isLogin: Boolean,
    val imageProfile: String
)