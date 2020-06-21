package rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels

import androidx.lifecycle.ViewModel
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.LocationRepository
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.LocationContract

class LocationViewModel(
    private val locationRepository: LocationRepository
) : ViewModel(), LocationContract.ViewModel {





}