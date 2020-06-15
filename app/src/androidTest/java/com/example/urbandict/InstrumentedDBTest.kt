package com.example.urbandict

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.db.UrbanDictDB
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class InstrumentedDBTest {

    private lateinit var testDB: UrbanDictDB

    @Before
    fun createDB() {
        val context: Context = ApplicationProvider.getApplicationContext()
        testDB = Room.inMemoryDatabaseBuilder(context, UrbanDictDB::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        testDB.close()
    }

    @Test
    fun testInsertDefinition() {
        val testDefinition: DefinitionItem = DefinitionItem(1, "A definition", 1, "A term", 1)
        testDB.defsDao().insert(testDefinition)
        val testDBValue = testDB.defsDao().getDefinitions("A term").blockingGet()[0]
        assertEquals(testDefinition, testDBValue)
    }
}