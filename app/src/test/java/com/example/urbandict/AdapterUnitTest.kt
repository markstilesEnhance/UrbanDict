package com.example.urbandict

import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.view.MainListAdapter
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class AdapterUnitTest {

    var adapter = MainListAdapter()

    @Before
    fun emptyAdapter() {
        val emptyDefinitions: MutableList<DefinitionItem> = mutableListOf()
        adapter.updateDefinitions(emptyDefinitions)
    }

    @Test
    fun testInsertSize() {
        val testDefinitions: MutableList<DefinitionItem> = mutableListOf(
            DefinitionItem(0,"A definition", 1, "A term", 1),
            DefinitionItem(0, "Another definition", 2, "Another term", 2),
            DefinitionItem(0, "3rd definition", 3, "3rd term", 3)
        )
        adapter.updateDefinitions(testDefinitions)
        assertEquals(3, adapter.itemCount)
    }
}