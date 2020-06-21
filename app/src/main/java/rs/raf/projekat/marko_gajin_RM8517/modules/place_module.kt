package rs.raf.projekat.marko_gajin_RM8517.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.local.Database
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.PlaceRepository
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.PlaceRepositoryImpl
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.PlaceViewModel

val placeModule = module {

    viewModel { PlaceViewModel(get()) }

    single<PlaceRepository> { PlaceRepositoryImpl(get()) }
    
    single { get<Database>().getLocationDao() }
}