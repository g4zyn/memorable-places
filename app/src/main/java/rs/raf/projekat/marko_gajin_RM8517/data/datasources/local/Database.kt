package rs.raf.projekat.marko_gajin_RM8517.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat.marko_gajin_RM8517.data.models.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 2,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun getLocationDao(): PlaceDao
}