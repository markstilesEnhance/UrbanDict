package com.example.urbandict.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandict.R
import com.example.urbandict.di.UrbanApplication
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.viewmodel.MainViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private var adapter = MainListAdapter()

    @Parcelize
    enum class SortDirection : Parcelable {
        UP, DOWN, NONE
    }

    private var sortMode: SortDirection = SortDirection.NONE
    private val WHITE = 255255255

    override fun onCreate(savedInstanceState: Bundle?) {
        UrbanApplication.urbanComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = adapter

        viewModel.getState().observe(this, Observer { appState ->
            when (appState) {
                is MainViewModel.AppState.LOADING -> {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                    sort_down_button.visibility = View.GONE
                    sort_up_button.visibility = View.GONE
                }
                is MainViewModel.AppState.ERROR -> {
                    Toast.makeText(this, getString(R.string.error, appState.error), Toast.LENGTH_SHORT).show()
                    sort_down_button.visibility = View.GONE
                    sort_up_button.visibility = View.GONE
                }
                is MainViewModel.AppState.SUCCESS -> {
                    sort_down_button.visibility = View.VISIBLE
                    sort_up_button.visibility = View.VISIBLE
                    displayDefinitions(appState.defList)
                    if(sortMode == SortDirection.UP) sortByThumbsUp()
                    else if(sortMode == SortDirection.DOWN) sortByThumbsDown()
                }
            }
        })

        search_button.setOnClickListener {
            val searchTerm: String = search_term.text.toString()
            sortMode = SortDirection.NONE
            sort_up_button.setBackgroundColor(WHITE)
            sort_down_button.setBackgroundColor(WHITE)
            viewModel.getDefinitions(searchTerm)
        }

        sort_up_button.setOnClickListener {
            sortByThumbsUp()
        }

        sort_down_button.setOnClickListener {
            sortByThumbsDown()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("term", search_term.text.toString())
        outState.putParcelable("sort", sortMode)
        if(sort_up_button.visibility == View.VISIBLE) {
            outState.putInt("hasSearched", 1)
        } else {
            outState.putInt("hasSearched", 0)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        search_term.setText(savedInstanceState.getString("term"))
        sortMode = savedInstanceState.getParcelable("sort") ?: SortDirection.NONE
        if(savedInstanceState.getInt("hasSearched") == 1) {
            sort_up_button.setBackgroundColor(WHITE)
            sort_down_button.setBackgroundColor(WHITE)
            viewModel.getDefinitions(search_term.text.toString())
        }
    }

    private fun displayDefinitions(list: MutableList<DefinitionItem>) {
        adapter.updateDefinitions(list)
    }

    private fun sortByThumbsUp() {
        adapter.sortUp()
        sortMode = SortDirection.UP
        sort_up_button.setBackgroundColor(getColor(R.color.colorAccent))
        sort_down_button.setBackgroundColor(WHITE)
    }

    private fun sortByThumbsDown() {
        adapter.sortDown()
        sortMode = SortDirection.DOWN
        sort_down_button.setBackgroundColor(getColor(R.color.colorAccent))
        sort_up_button.setBackgroundColor(WHITE)
    }

}
