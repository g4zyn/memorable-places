package rs.raf.projekat.marko_gajin_RM8517.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.local.Database

val coreModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(androidApplication().packageName, Context.MODE_PRIVATE)
    }

    single { Room.databaseBuilder(androidContext(), Database::class.java, "MemorablePlacesDb")
        .fallbackToDestructiveMigration()
        .build() }

}

inline fun <reified T> create(retrofit: Retrofit): T {
    return retrofit.create<T>(T::class.java)
}