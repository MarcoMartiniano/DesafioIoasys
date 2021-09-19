package br.com.marco.desafioioasys.data.repositories

import android.os.RemoteException
import br.com.marco.desafioioasys.data.services.EnterpriseService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class EnterpriseRepositoryImpl(private val service: EnterpriseService) : EnterpriseRepository {
    override suspend fun listEnterprises(
        token: String,
        client: String,
        uid: String
    ) = flow {
        try {
            val repoList = service.listEnterprises(token,client,uid)
            emit(repoList)
        } catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }


}