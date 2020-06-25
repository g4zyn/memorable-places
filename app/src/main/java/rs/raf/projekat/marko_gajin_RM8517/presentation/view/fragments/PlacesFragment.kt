package rs.raf.projekat.marko_gajin_RM8517.presentation.view.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_places_sheet.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat.marko_gajin_RM85.R
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.PlaceContract
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.adapters.PlaceAdapter
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.states.PlacesState
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.PlaceViewModel
import timber.log.Timber

class PlacesFragment : Fragment(R.layout.fragment_places) {

    private val placeViewModel: PlaceContract.ViewModel by sharedViewModel<PlaceViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var adapter: PlaceAdapter

    private var mMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initMap()
        initBottomSheet()
        initRecycler()
        initListeners()
    }

    private fun initObservers() {
        placeViewModel.placesState.observe(viewLifecycleOwner, Observer {
            Timber.e("Place - $it")
            renderState(it)
        })
        placeViewModel.getPlaces()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.places_map) as SupportMapFragment?
        mapFragment?.getMapAsync(setMap)
    }

    private val setMap = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        setMapStyle()
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                headerBtn.rotation = slideOffset * 180
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
        })
    }

    private fun initRecycler() {
        placesRv.layoutManager = LinearLayoutManager(context)
        adapter = PlaceAdapter()
        placesRv.adapter = adapter
    }

    private fun initListeners() {
        mMap?.setOnMarkerClickListener { showLocation(it) }
        bottomSheet.setOnClickListener { collapseBottomSheet(it) }
    }

    private val showLocation = { marker: Marker ->
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()
//            TODO hide location info sheet
        } else {
            marker.showInfoWindow()
//            TODO show location info sheet
        }
        true
    }

    private val collapseBottomSheet = { _: View ->
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun renderState(state: PlacesState) {
        when (state) {
            is PlacesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.places)
                state.places.forEach { markLocation(it) }
            }
            is PlacesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is PlacesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private val markLocation = { place: Place ->
        val location = LatLng(place.latitude, place.longitude)
        val markerOptions = MarkerOptions().position(location).title(place.name)
        mMap?.addMarker(markerOptions)
    }

    private fun showLoadingState(loading: Boolean) {
//        TODO implement loading state
    }

    private fun setMapStyle() {
        try {
            val success = mMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context, R.raw.mapstyle
                )
            )
            if (!success!!) {
                Timber.e("Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Timber.e(e, "Can't find style. Error: ")
        }
    }

}