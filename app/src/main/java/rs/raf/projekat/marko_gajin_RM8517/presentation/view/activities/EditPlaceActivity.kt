package rs.raf.projekat.marko_gajin_RM8517.presentation.view.activities

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_edit_place.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat.marko_gajin_RM85.R
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.PlaceContract
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.PlaceViewModel
import timber.log.Timber

class EditPlaceActivity : AppCompatActivity(R.layout.activity_edit_place) {

    private val placeViewModel: PlaceContract.ViewModel by viewModel<PlaceViewModel>()

    private var place: Place? = null

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        initMap()
        initPlace()
        initListeners()
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.selected_place_map) as SupportMapFragment?
        mapFragment?.getMapAsync(setMap)
    }

    private val setMap = OnMapReadyCallback {
        mMap = it
        setMapStyle()
    }

    private fun initPlace() {
        intent.let {
            place = placeViewModel.getPlaceData(it)
            nameEt.setText(place?.name)
            descEt.setText(place?.description)
            markLocation()
        }
    }

    private fun markLocation() {
        val location = LatLng(place!!.latitude, place!!.longitude)
        val markerOptions = MarkerOptions().position(location).title(place?.name)
        mMap?.addMarker(markerOptions)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }

    private fun initListeners() {
        cancelBtn.setOnClickListener { finish() }
        saveBtn.setOnClickListener { saveChanges(it) }
    }

    private val saveChanges = { _: View ->
        val name = nameEt.text.toString()
        val description = descEt.text.toString()
        val changedPlace = place!!.copy(
            name = name,
            description = description
        )
        Timber.e("Edited - $changedPlace")
        placeViewModel.editPlace(changedPlace)
        finish()
    }

    private fun setMapStyle() {
        try {
            val success = mMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.mapstyle
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