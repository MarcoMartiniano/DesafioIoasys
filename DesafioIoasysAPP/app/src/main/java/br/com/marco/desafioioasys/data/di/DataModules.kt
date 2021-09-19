package br.com.marco.desafioioasys.data.di


import br.com.marco.desafioioasys.data.repositories.EnterpriseRepository
import br.com.marco.desafioioasys.data.repositories.EnterpriseRepositoryImpl
import br.com.marco.desafioioasys.data.repositories.LoginRepository
import br.com.marco.desafioioasys.data.repositories.LoginRepositoryImpl
import br.com.marco.desafioioasys.data.services.EnterpriseService
import br.com.marco.desafioioasys.data.services.LoginService
import br.com.marco.desafioioasys.data.services.RetrofitClient
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


object DataModules {



    fun load() {
        loadKoinModules(repositoryModule() + networkModule() )
    }

    private fun repositoryModule(): Module {
        return module {
            single<LoginRepository> { LoginRepositoryImpl(get()) }
            single<EnterpriseRepository> { EnterpriseRepositoryImpl(get()) }
        }

    }


    private fun networkModule(): Module {
        return module {

            single { RetrofitClient.createService(LoginService::class.java) }
            single { RetrofitClient.createService(EnterpriseService::class.java) }
        }
    }




}