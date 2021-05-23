package com.andreandyp.fintonicandroidchallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andreandyp.fintonicandroidchallenge.database.entities.BeerEntity

@Database(entities = [BeerEntity::class], version = 1, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beersDao(): BeersDao

    companion object {
        @Volatile
        private var INSTANCE: BeerDatabase? = null

        fun getDatabase(context: Context): BeerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BeerDatabase::class.java,
                    "beer_database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}