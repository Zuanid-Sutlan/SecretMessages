package com.example.secretmessagesencrypter

import android.app.Application
import com.example.secretmessagesencrypter.di.Module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppClass)
            modules(appModule)
        }
    }
}
