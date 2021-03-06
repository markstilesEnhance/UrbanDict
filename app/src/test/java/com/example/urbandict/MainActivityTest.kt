package com.example.urbandict

import android.view.View
import com.example.urbandict.view.MainActivity
import com.example.urbandict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.Robolectric

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    private lateinit var activityController: ActivityController<MainActivity>

    @Mock
    private lateinit var viewModel: MainViewModel

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        activityController = Robolectric.buildActivity(MainActivity::class.java)
        activity = activityController.get()

        activityController.create()
        activity.viewModel = viewModel
        activityController.start()
    }

    @Test
    fun testSortButtonsHiddenInitially() {
        assertEquals(View.GONE, activity.sort_down_button.visibility)
        assertEquals(View.GONE, activity.sort_up_button.visibility)
    }

}