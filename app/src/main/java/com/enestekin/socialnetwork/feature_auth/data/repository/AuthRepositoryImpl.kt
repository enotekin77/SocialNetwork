package com.enestekin.socialnetwork.feature_auth.data.repository

import android.content.SharedPreferences
import com.enestekin.socialnetwork.R
import com.enestekin.socialnetwork.core.util.Constants.KEY_JWT_TOKEN
import com.enestekin.socialnetwork.core.util.Resource
import com.enestekin.socialnetwork.core.util.SimpleResource
import com.enestekin.socialnetwork.core.util.UiText
import com.enestekin.socialnetwork.feature_auth.data.remote.AuthApi
import com.enestekin.socialnetwork.feature_auth.data.remote.request.CreateAccountRequest
import com.enestekin.socialnetwork.feature_auth.data.remote.request.LoginRequest
import com.enestekin.socialnetwork.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        println("Enes 2")

        val request = CreateAccountRequest(email, username, password)
        return try {
            println("Enes")
            val response = api.register(request)
            println("Enes 3")

            println(response.successful)
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun login(email: String, password: String): SimpleResource {

        val request = LoginRequest(email, password)
        return try {
            val response = api.login(request)
            if (response.successful) {
                response.data?.token?.let { token ->

                    sharedPreferences.edit()
                        .putString(KEY_JWT_TOKEN, token)
                        .apply()
                }
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun authenticate(): SimpleResource {


        return try {
            api.authenticate()
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            println("Burasi calisti")
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )

        }


    }
}