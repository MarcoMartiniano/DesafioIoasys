package br.com.marco.desafioioasys.data.repositories

import br.com.marco.desafioioasys.data.model.AuthenticationResponse
import br.com.marco.desafioioasys.data.model.LoginRequest
import br.com.marco.desafioioasys.data.services.LoginService
import retrofit2.Response

class LoginRepositoryImpl (private val service: LoginService) : LoginRepository {
    override suspend fun executeLogin(email: String, password: String): Response<AuthenticationResponse> {
        return service.login(LoginRequest(email, password))
    }
}