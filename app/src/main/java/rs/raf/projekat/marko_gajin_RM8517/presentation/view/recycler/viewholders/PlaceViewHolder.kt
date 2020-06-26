package rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_place_item.*
import kotlinx.android.synthetic.main.layout_place_item.view.*
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

class PlaceViewHolder(
    override val containerView: View,
    private val onLocationBtnClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

     init {
        initListeners()
     }

    private fun initListeners() {
        containerView.markerImg.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onLocationBtnClicked.invoke(adapterPosition)
            }
        }
    }

    fun bind(place: Place) {
        itemName.text = place.name
        itemDesc.text = place.description
        locationTv.text = "${place.latitude}, ${place.longitude}"
    }
}