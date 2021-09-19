package br.com.marco.desafioioasys.domain

import br.com.marco.desafioioasys.data.model.AuthenticationResponse
import br.com.marco.desafioioasys.data.repositories.LoginRepository
import retrofit2.Response

class ExecuteLoginUseCase (
    private val loginRepository: LoginRepository
        ): LoginUseCase{
    override suspend fun invoke(email: String, password: String): Response<AuthenticationResponse> {
        return loginRepository.executeLogin(email, password)
    }

}


interface LoginUseCase {
    suspend operator fun invoke(
        email: String, password: String
    ): Response<AuthenticationResponse>
}