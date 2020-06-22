package rs.raf.projekat.marko_gajin_RM8517.presentation.view.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_home_sheet.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat.marko_gajin_RM85.R
import rs.raf.projekat.marko_gajin_RM8517.presentation.contracts.PlaceContract
import rs.raf.projekat.marko_gajin_RM8517.presentation.viewmodels.PlaceViewModel
import timber.log.Timber

class PlacesFragment : Fragment(R.layout.fragment_places) {

    private val placeViewModel: PlaceContract.ViewModel by sharedViewModel<PlaceViewModel>()

    private var mMap: GoogleMap? = null

    private val setMap = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        setMapStyle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initListeners()
    }

    private fun initUi() {
        initMap()
        initBottomSheet()
    }

    private fun initListeners() {}

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.places_map) as SupportMapFragment?
        mapFragment?.getMapAsync(setMap)
    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheet.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                headerBtn.rotation = slideOffset * 180
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
        })
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