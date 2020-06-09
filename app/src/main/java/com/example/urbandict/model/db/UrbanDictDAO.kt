package com.example.urbandict.model.db

import androidx.room.*
import com.example.urbandict.model.DefinitionItem
import io.reactivex.Single

@Dao
interface UrbanDictDAO {

    @Query("SELECT * FROM definitions_table WHERE word = :searchTerm")
    fun getDefinitions(searchTerm: String): Single<MutableList<DefinitionItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(def: DefinitionItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(defs: MutableList<DefinitionItem>)

    @Delete
    fun delete(def: DefinitionItem)

    @Update
    fun update(def: DefinitionItem)

}