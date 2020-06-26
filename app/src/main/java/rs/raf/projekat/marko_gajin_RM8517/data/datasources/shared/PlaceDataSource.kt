package rs.raf.projekat.marko_gajin_RM8517.data.datasources.shared

import android.content.Intent
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

interface PlaceDataSource {

    fun setPlaceData(place: Place, intent: Intent)
    fun getPlaceData(intent: Intent): Place?
}