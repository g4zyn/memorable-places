package rs.raf.projekat.marko_gajin_RM8517.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat.marko_gajin_RM8517.data.models.LocationEntity

@Dao
abstract class LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: LocationEntity): Completable

    @Query("SELECT * FROM places")
    abstract fun getAll(): Observable<List<LocationEntity>>

    @Query("SELECT * FROM places WHERE name LIKE :search || '%' or description LIKE :search || '%'")
    abstract fun getBySearch(search: String): Observable<List<LocationEntity>>

    @Query("DELETE FROM places WHERE id == :id")
    abstract fun delete(id: Long): Completable

    @Update
    abstract fun update(locationEntity: LocationEntity): Completable

}