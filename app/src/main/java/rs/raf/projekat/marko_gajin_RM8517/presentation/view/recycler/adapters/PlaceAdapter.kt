package rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat.marko_gajin_RM85.R
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.diff.PlaceDiffCallback
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.viewholders.PlaceViewHolder

class PlaceAdapter(
    private val onLocationBtnClicked: (Place) -> Unit
) : ListAdapter<Place, PlaceViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_place_item, parent, false)

        return PlaceViewHolder(
            view
        ) { onLocationBtnClicked.invoke(getItem(it)) }
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}