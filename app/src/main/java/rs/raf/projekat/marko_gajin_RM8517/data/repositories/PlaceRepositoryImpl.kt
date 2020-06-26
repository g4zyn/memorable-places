package rs.raf.projekat.marko_gajin_RM8517.data.repositories

import android.content.Intent
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.local.PlaceDao
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.shared.PlaceDataSource
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.data.models.PlaceEntity

class PlaceRepositoryImpl(
    private val localDataSource: PlaceDao,
    private val sharedDataSource: PlaceDataSource
) : PlaceRepository {

    private val mapLocations = { entities: List<PlaceEntity> ->
        entities.map { Place(it.id, it.name, it.description, it.latitude, it.longitude) }
    }

    override fun getAll(): Observable<List<Place>> {
        return localDataSource.getAll().map { mapLocations(it) }
    }

    override fun getBySearch(search: String): Observable<List<Place>> {
        return localDataSource.getBySearch(search).map { mapLocations(it) }
    }

    override fun insert(place: Place): Completable {
        val locationEntity = PlaceEntity(
            place.id,
            place.name,
            place.description,
            place.latitude,
            place.longitude
        )
        return localDataSource.insert(locationEntity)
    }

    override fun edit(place: Place): Completable {
        return Completable.fromCallable {
            val oldLocation = localDataSource.getById(place.id)
            val newLocation = oldLocation.copy(
                name = place.name,
                description = place.description
            )
            localDataSource.update(newLocation)
        }
    }

    override fun delete(place: Place): Completable {
        return localDataSource.delete(place.id)
    }

    override fun getPlaceData(intent: Intent): Place? {
        return sharedDataSource.getPlaceData(intent)
    }

    override fun setPlaceData(place: Place, intent: Intent) {
        sharedDataSource.setPlaceData(place, intent)
    }
}