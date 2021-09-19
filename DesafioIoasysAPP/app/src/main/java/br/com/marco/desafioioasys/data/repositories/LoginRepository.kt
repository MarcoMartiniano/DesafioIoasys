package br.com.marco.desafioioasys.data.repositories

import br.com.marco.desafioioasys.data.model.AuthenticationResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun executeLogin(email: String, password: String) : Response<AuthenticationResponse>
}