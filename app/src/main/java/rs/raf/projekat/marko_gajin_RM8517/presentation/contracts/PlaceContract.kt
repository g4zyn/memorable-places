package rs.raf.projekat.marko_gajin_RM8517.presentation.contracts

import android.content.Intent
import androidx.lifecycle.LiveData
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.AddPlaceState
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.PlacesState

interface PlaceContract {

    interface ViewModel {

        val placesState: LiveData<PlacesState>
        val addDone: LiveData<AddPlaceState>

        fun getPlaces()
        fun savePlace(place: Place)
        fun searchPlaces(search: String)
        fun editPlace(place: Place)
//        fun deletePlace(location: Location)

        fun getPlaceData(intent: Intent): Place?
        fun setPlaceData(place: Place, intent: Intent)
    }

}