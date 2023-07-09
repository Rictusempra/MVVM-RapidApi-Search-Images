package com.foodsoul.test

import android.app.Application
import android.content.Context
import com.foodsoul.test.di.AppComponent
import com.foodsoul.test.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
        internal lateinit var appContext: Context
    }

}