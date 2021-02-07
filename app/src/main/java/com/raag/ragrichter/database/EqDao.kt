package com.raag.ragrichter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raag.ragrichter.data.Earthquake

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("SELECT * FROM earthquakes")
    fun getEartquake(): MutableList<Earthquake>

    @Query("SELECT * FROM earthquakes order by magnitude ASC")
    fun getEartquakeByMagnitude(): MutableList<Earthquake>
}