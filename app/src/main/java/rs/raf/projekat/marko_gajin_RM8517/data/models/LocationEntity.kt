package rs.raf.projekat.marko_gajin_RM8517.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)