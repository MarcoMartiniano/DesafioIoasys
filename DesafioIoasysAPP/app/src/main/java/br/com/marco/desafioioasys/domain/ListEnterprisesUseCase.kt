package br.com.marco.desafioioasys.domain

import br.com.marco.desafioioasys.data.model.Enterprises
import br.com.marco.desafioioasys.data.repositories.EnterpriseRepository
import kotlinx.coroutines.flow.Flow


class ListEnterprisesUseCase (
    private val enterpriseRepository: EnterpriseRepository
): ListUseCase{
    override suspend fun invoke(token: String, client: String, uid: String): Flow<Enterprises> {
        return enterpriseRepository.listEnterprises(token,client,uid)
    }
}


interface ListUseCase {
    suspend operator fun invoke(
        token: String, client: String, uid: String
    ): Flow<Enterprises>
}




