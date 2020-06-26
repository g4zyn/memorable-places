package rs.raf.projekat.marko_gajin_RM8517.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.local.Database
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.shared.PlaceDataSource
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.shared.PlaceIntentDataSource
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.PlaceRepository
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.PlaceRepositoryImpl
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.PlaceViewModel

val placeModule = module {

    viewModel { PlaceViewModel(get()) }

    single<PlaceRepository> { PlaceRepositoryImpl(get(), sharedDataSource = get(named("intent"))) }

    single<PlaceDataSource>(named("intent")) { PlaceIntentDataSource() }

    single { get<Database>().getLocationDao() }
}