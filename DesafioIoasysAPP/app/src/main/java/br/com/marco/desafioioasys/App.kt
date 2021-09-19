package br.com.marco.desafioioasys

import android.app.Application
import br.com.marco.desafioioasys.data.di.DataModules
import br.com.marco.desafioioasys.domain.di.DomainModule
import br.com.marco.desafioioasys.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

       DataModules.load()
        DomainModule.load()
        PresentationModule.load()
    }
}