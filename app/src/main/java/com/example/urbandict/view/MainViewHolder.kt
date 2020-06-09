package com.example.urbandict.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandict.model.DefinitionItem
import kotlinx.android.synthetic.main.definitions_item.view.*

class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(definition: DefinitionItem) {
        itemView.term.text = definition.word
        itemView.up_count.text = definition.thumbs_up.toString()
        itemView.down_count.text = definition.thumbs_down.toString()
        itemView.definition.text = definition.definition.replace("[", "").replace("]", "")
    }

}