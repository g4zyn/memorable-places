package rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.PlaceRepository
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.PlaceContract
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.AddPlaceState
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.PlacesState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PlaceViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel(), PlaceContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val placesState: MutableLiveData<PlacesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddPlaceState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .switchMap {
                placeRepository
                    .getBySearch(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    placesState.value = PlacesState.Success(it)
                },
                {
                    placesState.value = PlacesState.Error("Error happened while fetching data from database")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getPlaces() {
        val subscription = placeRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    placesState.value = PlacesState.Success(it)
                },
                {
                    placesState.value = PlacesState.Error("Unable to get data from database")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun savePlace(place: Place) {
        val subscription = placeRepository
            .insert(place)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddPlaceState.Success
                },
                {
                    addDone.value = AddPlaceState.Error("Unable to save location")
                }
            )
        subscriptions.add(subscription)
    }

    override fun searchPlaces(search: String) {
        publishSubject.onNext(search)
    }

    override fun editPlace(place: Place) {
        val subscription = placeRepository
            .edit(place)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("UPDATED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getPlaceData(intent: Intent): Place? {
        return placeRepository.getPlaceData(intent)
    }

    override fun setPlaceData(place: Place, intent: Intent) {
        placeRepository.setPlaceData(place, intent)
    }
}