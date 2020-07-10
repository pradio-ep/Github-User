package com.pradioep.githubuser

import android.app.Application
import com.pradioep.githubuser.di.NetworkModule
import com.pradioep.githubuser.di.RepositoryModule
import com.pradioep.githubuser.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.EmptyLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            if (BuildConfig.DEBUG) androidLogger() else EmptyLogger()
            androidContext(this@App)
            modules(
                listOf(
                    NetworkModule,
                    RepositoryModule,
                    ViewModelModule
                )
            )
        }
    }
}