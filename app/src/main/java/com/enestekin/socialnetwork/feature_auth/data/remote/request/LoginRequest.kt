package com.enestekin.socialnetwork.feature_auth.data.remote.request

data class LoginRequest(
    val email: String,
    val password: String
)