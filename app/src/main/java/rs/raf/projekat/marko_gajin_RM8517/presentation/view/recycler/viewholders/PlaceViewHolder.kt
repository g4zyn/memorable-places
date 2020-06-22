package rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_place_item.*
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

class PlaceViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

     init {
        initListeners()
     }

    private fun initListeners() {}

    fun bind(place: Place) {
        nameTv.text = place.name
        descTv.text = place.description
        locationTv.text = "${place.latitude}, ${place.longitude}"
    }
}