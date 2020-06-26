package rs.raf.projekat.marko_gajin_RM8517.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val id: Long,
    var name: String,
    var description: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable