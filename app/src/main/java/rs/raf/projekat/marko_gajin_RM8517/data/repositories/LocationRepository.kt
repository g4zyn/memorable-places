package rs.raf.projekat.marko_gajin_RM8517.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat.marko_gajin_RM8517.data.models.Location

interface LocationRepository {

    fun getAll(): Observable<List<Location>>
    fun getBySearch(search: String): Observable<List<Location>>
    fun insert(location: Location): Completable
    fun edit(location: Location): Completable
    fun delete(location: Location): Completable

}