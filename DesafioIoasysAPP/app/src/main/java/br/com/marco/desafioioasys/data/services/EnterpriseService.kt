package br.com.marco.desafioioasys.data.services

import br.com.marco.desafioioasys.data.model.Enterprises
import br.com.marco.desafioioasys.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header


interface EnterpriseService {
    @GET("enterprises/")
    suspend fun listEnterprises(
        @Header(Constants.ACCESS_TOKEN) token: String,
        @Header(Constants.CLIENT) client: String,
        @Header(Constants.UID) uid: String
    ): Enterprises
}