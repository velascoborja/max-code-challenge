package com.touchsurgery.database.di

import androidx.room.Room
import com.touchsurgery.database.DigitalSurgeryDatabase
import com.touchsurgery.database.usecases.GetProcedureIdsUseCase
import com.touchsurgery.database.usecases.ObserveProceduresUseCase
import com.touchsurgery.database.usecases.RemoveProcedureUseCase
import com.touchsurgery.database.usecases.SaveProcedureUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val moduleDatabase = module {

    single<DigitalSurgeryDatabase> {
        Room.databaseBuilder(
            androidContext(),
            DigitalSurgeryDatabase::class.java,
            "digital_surgery_database.db",
        ).build()
    }

    factory {
        ObserveProceduresUseCase(
            db = get(),
        )
    }

    factory {
        RemoveProcedureUseCase(
            db = get(),
        )
    }

    factory {
        SaveProcedureUseCase(
            db = get(),
        )
    }

    factory {
        GetProcedureIdsUseCase(
            db = get(),
        )
    }
}
