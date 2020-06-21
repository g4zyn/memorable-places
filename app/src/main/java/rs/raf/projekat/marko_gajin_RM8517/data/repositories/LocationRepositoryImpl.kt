package rs.raf.projekat.marko_gajin_RM8517.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat.marko_gajin_RM8517.data.datasources.local.LocationDao
import rs.raf.projekat.marko_gajin_RM8517.data.models.Location
import rs.raf.projekat.marko_gajin_RM8517.data.models.LocationEntity

class LocationRepositoryImpl(
    private val localDataSource: LocationDao
) : LocationRepository {

    private val mapLocations = { entities: List<LocationEntity> ->
        entities.map { Location(it.id, it.name, it.description, it.latitude, it.longitude) }
    }

    override fun getAll(): Observable<List<Location>> {
        return localDataSource.getAll().map { mapLocations(it) }
    }

    override fun getBySearch(search: String): Observable<List<Location>> {
        return localDataSource.getBySearch(search).map { mapLocations(it) }
    }

    override fun insert(location: Location): Completable {
        val locationEntity = LocationEntity(
            location.id,
            location.name,
            location.description,
            location.latitude,
            location.longitude
        )
        return localDataSource.insert(locationEntity)
    }

    override fun edit(location: Location): Completable {
        return Completable.fromCallable {
            val oldLocation = localDataSource.getById(location.id)
            val newLocation = oldLocation.copy(
                name = location.name,
                description = location.description
            )
            localDataSource.update(newLocation)
        }
    }

    override fun delete(location: Location): Completable {
        return localDataSource.delete(location.id)
    }
}