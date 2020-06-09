package com.example.urbandict.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandict.R
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.UrbanDictionaryResponse

class MainListAdapter(): RecyclerView.Adapter<MainViewHolder>() {

    private var items: MutableList<DefinitionItem> = mutableListOf()

    fun updateDefinitions(defs: MutableList<DefinitionItem>) {
        this.items.clear()
        this.items = defs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.definitions_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }
}