package com.example.urbandict.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandict.R
import com.example.urbandict.di.UrbanApplication
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.UrbanDictionaryResponse
import com.example.urbandict.model.network.UrbanDictRepository
import com.example.urbandict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.definitions_item.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private var adapter = MainListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        UrbanApplication.urbanComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = adapter

        viewModel.getState().observe(this, Observer { appState ->
            when (appState) {
                is MainViewModel.AppState.LOADING -> {
                    Toast.makeText(this, "Loading definitions", Toast.LENGTH_SHORT).show()
                    sort_down_button.visibility = View.GONE
                    sort_up_button.visibility = View.GONE
                }
                is MainViewModel.AppState.ERROR -> {
                    Toast.makeText(this, "Error: ${appState.error}", Toast.LENGTH_SHORT).show()
                    sort_down_button.visibility = View.GONE
                    sort_up_button.visibility = View.GONE
                }
                is MainViewModel.AppState.SUCCESS -> {
                    sort_down_button.visibility = View.VISIBLE
                    sort_up_button.visibility = View.VISIBLE
                    displayDefinitions(appState.defList)
                }
            }
        })

        search_button.setOnClickListener {
            val searchTerm: String = search_term.text.toString()
            viewModel.getDefinitions(searchTerm)
            sort_up_button.setBackgroundColor(255255255)
            sort_down_button.setBackgroundColor(255255255)
        }

        sort_up_button.setOnClickListener {
            sortByThumbsUp()
            it.setBackgroundColor(getColor(R.color.colorAccent))
            sort_down_button.setBackgroundColor(255255255)
        }

        sort_down_button.setOnClickListener {
            sortByThumbsDown()
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
            sort_up_button.setBackgroundColor(255255255)
        }
    }

    private fun displayDefinitions(list: MutableList<DefinitionItem>) {
        adapter.updateDefinitions(list)
    }

    private fun sortByThumbsUp() {
        adapter.sortUp()
    }

    private fun sortByThumbsDown() {
        adapter.sortDown()
    }

}
