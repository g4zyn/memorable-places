package rs.raf.projekat.marko_gajin_RM8517.data.datasources.shared

import android.content.Intent
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

class PlaceIntentDataSource : PlaceDataSource {

    companion object {
        const val PLACE_KEY = "placeKey"
    }

    override fun setPlaceData(place: Place, intent: Intent) {
        intent.putExtra(PLACE_KEY, place)
    }

    override fun getPlaceData(intent: Intent): Place? {
        return intent.getParcelableExtra(PLACE_KEY)
    }
}