package com.example.nytopstories

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var section = ""
    var webHotUrl = ""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService
        dialog = SpotsDialog(this)
        swipe_refresh.setOnRefreshListener { loadNews(section, true) }

        diagonalLayout.setOnClickListener{
            val detail = Intent(baseContext,NewsDetail::class.java)
            detail.putExtra("webURL", webHotUrl)
            baseContext.startActivity(detail)

        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if(intent != null)
        {
            section = intent.getStringExtra("section")
            if(!section.isEmpty())
                loadNews(section, false)

        }
    }

    private fun loadNews(section: String?, isRefreshed: Boolean){
        if(isRefreshed)
        {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(section!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<News>?, response: Response<News>) {

                        dialog.dismiss()
                        Picasso.with(baseContext)
                            .load(response.body()!!.multimedia!![0].url)
                            .into(top_image)
                        top_title.text = response.body()!!.multimedia!![0].caption
                        top_author.text = response.body()!!.multimedia!![0].type

                        webHotUrl = response!!.body()!!.multimedia!![0].url.toString()

                        val removeFirstItem = response!!.body()!!.multimedia
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter

                    }

                })
        }
        else
        {
            swipe_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(section!!))
                .enqueue(object : Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<News>?, response: Response<News>) {
                        swipe_refresh.isRefreshing = false

                        Picasso.with(baseContext)
                            .load(response.body()!!.multimedia!![0].url)
                            .into(top_image)
                        top_title.text = response.body()!!.multimedia!![0].caption
                        top_author.text = response.body()!!.multimedia!![0].type

                        webHotUrl = response!!.body()!!.multimedia!![0].url.toString()

                        val removeFirstItem = response!!.body()!!.multimedia
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter

                    }

                })
        }
    }
}
