package rs.raf.projekat.marko_gajin_RM8517.presentation.view.states

import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

sealed class PlacesState {

    object Loading: PlacesState()
    data class Success(val places: List<Place>): PlacesState()
    data class Error(val message: String): PlacesState()
}