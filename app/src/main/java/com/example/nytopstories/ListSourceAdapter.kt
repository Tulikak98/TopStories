package com.example.nytopstories

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nytopstories.Website

class ListSourceAdapter(private val context:Context, private val webSite: Website):
    RecyclerView.Adapter<ListSourceViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.sourcenews_layout,parent,false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return webSite.section!!.size

    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int ) {
        holder.source_title.text = webSite.section!![position].num_results
        holder.setItemClickListener(object : ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNews::class.java)
                intent.putExtra("section", webSite.section!![position].results)
                context.startActivity(intent)
            }
        })
    }

}