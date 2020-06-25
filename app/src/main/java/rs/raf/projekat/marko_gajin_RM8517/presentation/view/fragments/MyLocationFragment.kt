package rs.raf.projekat.marko_gajin_RM8517.presentation.view.fragments

import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_home_sheet.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat.marko_gajin_RM85.R
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.PlaceContract
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.PlaceViewModel
import timber.log.Timber

class MyLocationFragment : Fragment(R.layout.fragment_my_location) {

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 1
    }

    private val placeViewModel: PlaceContract.ViewModel by sharedViewModel<PlaceViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        initMap()
        initBottomSheet()
        initListeners()
    }

    private fun initMap() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.my_location_map) as SupportMapFragment?
        mapFragment?.getMapAsync(setMap)
    }

    private val setMap = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        setMapStyle()
        getDeviceLocation()
    }

    private fun getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE)
            return
        }
        mMap?.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location -> markLocation(location) }
    }

    private val markLocation = { location: Location ->
        lastLocation = location
        val currentLocation = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().position(currentLocation).title("Current location")
        mMap?.addMarker(markerOptions)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f))
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

    private fun initListeners() {
        bottomSheet.setOnClickListener { collapseBottomSheet(it) }
        saveBtn.setOnClickListener { savePlace(it) }
    }

    private val collapseBottomSheet = { _: View ->
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private val savePlace = { _: View ->
        val name = nameInput.text.toString()
        val description = descInput.text.toString()
        val newPlace = Place(
            0,
            name,
            description,
            lastLocation.latitude,
            lastLocation.longitude
        )
        placeViewModel.savePlace(newPlace)
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