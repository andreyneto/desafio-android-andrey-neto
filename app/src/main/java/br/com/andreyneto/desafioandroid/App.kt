package br.com.andreyneto.desafioandroid

import android.app.Application
import br.com.andreyneto.desafioandroid.di.networkModule
import br.com.andreyneto.desafioandroid.di.repositoryModule
import br.com.andreyneto.desafioandroid.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(listOf(viewModelModule, networkModule, repositoryModule))
        }
    }
}