package br.com.marco.desafioioasys.data.services

import br.com.marco.desafioioasys.data.model.AuthenticationResponse
import br.com.marco.desafioioasys.data.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/auth/sign_in")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthenticationResponse>
}