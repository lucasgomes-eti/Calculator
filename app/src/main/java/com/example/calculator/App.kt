package com.example.calculator

import android.app.Application
import com.example.calculator.ui.CalculatorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    private val appModule = module {
        viewModelOf(::CalculatorViewModel)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}