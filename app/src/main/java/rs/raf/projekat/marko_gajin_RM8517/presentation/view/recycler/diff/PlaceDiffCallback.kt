package rs.raf.projekat.marko_gajin_RM8517.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat.marko_gajin_RM8517.data.models.Place

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.name == newItem.name
                && oldItem.description == newItem.description
                && oldItem.latitude == newItem.latitude
                && oldItem.longitude == newItem.longitude
    }
}