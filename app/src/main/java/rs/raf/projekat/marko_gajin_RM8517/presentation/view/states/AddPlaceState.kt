package rs.raf.projekat.marko_gajin_RM8517.presentation.view.states

sealed class AddPlaceState {

    object Success: AddPlaceState()
    data class Error(val message: String): AddPlaceState()
}