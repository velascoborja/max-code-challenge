package com.touchsurgery.surgicalinsights

import android.app.Application
import com.touchsurgery.app.main.di.moduleMain
import com.touchsurgery.database.di.moduleDatabase
import com.touchsurgery.networking.di.moduleNetworking
import com.touchsurgery.procedures.di.moduleProcedures
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SurgicalInsightsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SurgicalInsightsApplication)

            modules(
                moduleNetworking,
                moduleDatabase,
                moduleProcedures,
                moduleMain,
            )
        }
    }
}
