package com.example.nytopstories

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

class ListNewsAdapter(val multimedia: MutableList<Multimedia>,private val context: Context): RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.newsdisplay_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return multimedia.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        Picasso.with(context)
            .load(multimedia[position].url)
            .into(holder.article_image)

        if(multimedia[position].caption!!.length > 65)
        {
            holder.article_title.text = multimedia[position].caption!!.substring(0,65) + "..."
        }
        else
        {
            holder.article_title.text = multimedia[position].caption!!
        }

        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context,NewsDetail::class.java)
                detail.putExtra("webURL", multimedia[position].url)
                context.startActivity(detail)

            }

        })

    }

}