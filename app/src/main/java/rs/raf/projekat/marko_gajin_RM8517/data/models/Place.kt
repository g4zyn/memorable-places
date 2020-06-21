package rs.raf.projekat.marko_gajin_RM8517.data.models

data class Place(
    val id: Long,
    var name: String,
    var description: String,
    val latitude: Double,
    val longitude: Double
)