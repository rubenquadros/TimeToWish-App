package io.github.rubenquadros.timetowish

import android.app.Application

class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        var instance: Application? = null
    }
}