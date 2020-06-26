package rs.raf.projekat.marko_gajin_RM8517.data.repositories

import android.content.Intent
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

interface PlaceRepository {

    fun getAll(): Observable<List<Place>>
    fun getBySearch(search: String): Observable<List<Place>>
    fun insert(place: Place): Completable
    fun edit(place: Place): Completable
    fun delete(place: Place): Completable
    fun getPlaceData(intent: Intent) : Place?
    fun setPlaceData(place: Place, intent: Intent)

}