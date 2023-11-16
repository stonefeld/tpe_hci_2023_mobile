package ar.edu.itba.grupo10.vfit

import android.app.Application

class MainApplication : Application() {

    companion object {

        lateinit var instance: MainApplication
            private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}