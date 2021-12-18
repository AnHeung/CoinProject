package kuma.coinproject

import android.app.Application
import kuma.coinproject.di.module.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CoinApp : Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) { Timber.plant(Timber.DebugTree()) }
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CoinApp)
            modules(moduleList)
        }
    }
}