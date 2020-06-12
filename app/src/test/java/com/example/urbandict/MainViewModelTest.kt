package com.example.urbandict

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.network.UrbanDictRepository
import com.example.urbandict.viewmodel.MainViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class MainViewModelTest {

    lateinit var classUnderTest: MainViewModel
    lateinit var appStateSuccessTest: MainViewModel.AppState.SUCCESS
    lateinit var appStateErrorTest: MainViewModel.AppState.ERROR
    val testData : MutableList<DefinitionItem> = mutableListOf(
        DefinitionItem(1, "A definition", 2, "A term", 3)
    )
    val testError: Throwable = UnknownHostException("https://mashape-community-urban-dictionary.p.rapidapi.com/")

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: UrbanDictRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        classUnderTest = MainViewModel(repo)
        appStateSuccessTest = MainViewModel.AppState.SUCCESS(testData)
        appStateErrorTest = MainViewModel.AppState.ERROR(testError.message!!)
    }

    @Test
    fun testGetDefinitionsSuccess() {
        Mockito.`when`(repo.getDictionary("A term")).thenReturn(Single.just(testData))

        classUnderTest.getDefinitions("A term")
        val state = classUnderTest.getState()
        assertEquals(state.value, appStateSuccessTest)
        Mockito.verify(repo).getDictionary("A term")
    }

    @Test(expected = UnknownHostException::class)
    fun testGetDefinitionsFailure() {
        Mockito.`when`(repo.getDictionary("A term")).thenAnswer{throw (testError)}

        classUnderTest.getDefinitions("A term")
        val state = classUnderTest.getState()
        assertEquals(state.value, appStateErrorTest)
    }

}