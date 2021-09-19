package br.com.marco.desafioioasys.presentation.di

import br.com.marco.desafioioasys.presentation.DetalheEmpresaViewModel
import br.com.marco.desafioioasys.presentation.HomeViewModel
import br.com.marco.desafioioasys.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            viewModel { MainViewModel(get()) }
            viewModel { HomeViewModel(get()) }
            viewModel { DetalheEmpresaViewModel(get()) }
        }
    }
}