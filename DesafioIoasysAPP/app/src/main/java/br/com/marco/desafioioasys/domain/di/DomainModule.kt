package br.com.marco.desafioioasys.domain.di

import br.com.marco.desafioioasys.domain.ExecuteLoginUseCase
import br.com.marco.desafioioasys.domain.ListEnterprisesUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    fun load() {
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { ExecuteLoginUseCase(get()) }
            factory { ListEnterprisesUseCase(get()) }
        }
    }
}