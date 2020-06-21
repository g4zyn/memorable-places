package rs.raf.projekat.marko_gajin_RM8517.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.local.Database
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.LocationRepository
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.LocationRepositoryImpl
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.LocationViewModel

val locationModule = module {

    viewModel { LocationViewModel(get()) }

    single<LocationRepository> { LocationRepositoryImpl(get()) }
    
    single { get<Database>().getLocationDao() }
}