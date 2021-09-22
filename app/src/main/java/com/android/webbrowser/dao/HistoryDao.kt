package com.android.webbrowser.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.webbrowser.model.History

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: History)


    @Query("SELECT * FROM History")
    fun getAll():List<History>

    @Query("SELECT * FROM History WHERE History.urlString LIKE '%' || :search || '%'")
    fun loadHistory(search: String?): List<History>

    @Query("DELETE FROM History WHERE History.id = :id")
    fun deleteById(id:Int):Int
}