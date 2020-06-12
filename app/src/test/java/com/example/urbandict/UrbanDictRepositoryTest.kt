package com.example.urbandict

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.UrbanDictionaryResponse
import com.example.urbandict.model.db.UrbanDictDAO
import com.example.urbandict.model.db.UrbanDictDB
import com.example.urbandict.model.network.ApiService
import com.example.urbandict.model.network.UrbanDictRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import rx.android.plugins.RxAndroidPlugins

class UrbanDictRepositoryTest {

    lateinit var classUnderTest: UrbanDictRepository
    val testData : MutableList<DefinitionItem> = mutableListOf(
        DefinitionItem(1, "A definition", 2, "A term", 3)
    )
    val testResponse: UrbanDictionaryResponse = UrbanDictionaryResponse(testData)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var database: UrbanDictDB

    @Mock
    lateinit var dao: UrbanDictDAO

    @Mock
    lateinit var service: ApiService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        classUnderTest = UrbanDictRepository(database, service)
        io.reactivex.android.plugins.RxAndroidPlugins
            .setInitMainThreadSchedulerHandler{scheduler -> Schedulers.trampoline()}
    }

    @Test
    fun testFoundInDB() {
        Mockito.`when`(database.defsDao()).thenReturn(dao)
        Mockito.`when`(database.defsDao().getDefinitions("A term"))
            .thenReturn(Single.just(testData))
        Mockito.`when`(service.getDefinitions("A term"))
            .thenReturn(Single.just(testResponse))

        val definitionsReturn: MutableList<DefinitionItem> =
            classUnderTest.getDictionary("A term").blockingGet()
        assertEquals(testData, definitionsReturn)
        Mockito.verify(service, never()).getDefinitions("A term")
    }

    @Test
    fun testNotFoundInDB() {
        Mockito.`when`(database.defsDao()).thenReturn(dao)
        Mockito.`when`(database.defsDao().getDefinitions("A term"))
            .thenReturn(Single.just(mutableListOf()))
        Mockito.`when`(service.getDefinitions("A term"))
            .thenReturn(Single.just(testResponse))

        val definitionsReturn: MutableList<DefinitionItem> =
            classUnderTest.getDictionary("A term").blockingGet()
        assertEquals(testData, definitionsReturn)
        Mockito.verify(service).getDefinitions("A term")
    }

}