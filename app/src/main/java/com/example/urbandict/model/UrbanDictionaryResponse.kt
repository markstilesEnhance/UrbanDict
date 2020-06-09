package com.example.urbandict.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class UrbanDictionaryResponse (

    val list: MutableList<DefinitionItem>

)

@Entity(tableName = "definitions_table")
data class DefinitionItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "definition")
    val definition: String,

    @ColumnInfo(name = "thumbs_up")
    val thumbs_up: Int,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "thumbs_down")
    val thumbs_down: Int

)