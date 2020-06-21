package rs.raf.projekat.marko_gajin_RM8517.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat.marko_gajin_RM8517.data.models.PlaceEntity

@Dao
abstract class PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: PlaceEntity): Completable

    @Query("SELECT * FROM places")
    abstract fun getAll(): Observable<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE id == :id")
    abstract fun getById(id: Long): PlaceEntity

    @Query("SELECT * FROM places WHERE name LIKE :search || '%' or description LIKE :search || '%'")
    abstract fun getBySearch(search: String): Observable<List<PlaceEntity>>

    @Query("DELETE FROM places WHERE id == :id")
    abstract fun delete(id: Long): Completable

    @Update
    abstract fun update(placeEntity: PlaceEntity): Completable

}