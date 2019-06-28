package com.example.nytopstories

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.sourcenews_layout.view.*
import com.example.nytopstories.ItemClickListener


class ListSourceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener:ItemClickListener

    var source_title = itemView.sourcenews_name

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v:View?){
        itemClickListener.onClick(v!!, adapterPosition)
    }

}