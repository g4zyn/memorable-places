package rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.data.repositories.PlaceRepository
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.PlaceContract
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.AddPlaceState
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.PlacesState
import timber.log.Timber

class PlaceViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel(), PlaceContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val placesState: MutableLiveData<PlacesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddPlaceState> = MutableLiveData()

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
        TODO("Not yet implemented")
    }

    override fun editPlace(place: Place) {
        TODO("Not yet implemented")
    }
}