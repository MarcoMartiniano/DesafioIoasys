package br.com.marco.desafioioasys.data.repositories


import br.com.marco.desafioioasys.data.model.Enterprises
import kotlinx.coroutines.flow.Flow

interface EnterpriseRepository {
    suspend fun listEnterprises(
        token: String,
        client: String,
        uid: String,
    ): Flow<Enterprises>
}