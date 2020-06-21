package rs.raf.projekat.marko_gajin_RM8517.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat.marko_gajin_RM8517.data.models.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}