package com.example.nytopstories

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.ThemedSpinnerAdapter
import android.widget.Toast
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)
        mService = Common.newsService
        swipe_refresh.setOnRefreshListener{
            loadWebsiteSource(true)
        }

        recyclerview_source.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerview_source.layoutManager = layoutManager
        dialog = SpotsDialog(this)
        loadWebsiteSource(false)
    }

    private fun loadWebsiteSource(isRefresh: Boolean){

        if (!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null")
            {
                //read cache
                val website = Gson().fromJson<Website>(cache, Website::class.java)
                adapter = ListSourceAdapter(baseContext, website)
                adapter.notifyDataSetChanged()
                recyclerview_source.adapter = adapter
            }
            else
            {
                //load website
                dialog.show()
                //fetch the data
                mService.section.enqueue(object:retrofit2.Callback<Website>{
                    override fun onFailure(call: Call<Website>, t: Throwable) {
                        Toast.makeText(baseContext, "failed",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Website>, response: Response<Website>) {
                       adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recyclerview_source.adapter = adapter
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))
                        dialog.dismiss()
                    }
                })
            }
        }

        else
        {
            swipe_refresh.isRefreshing= true
            //fetch the data
            mService.section.enqueue(object:retrofit2.Callback<Website>{
                override fun onFailure(call: Call<Website>, t: Throwable) {
                    Toast.makeText(baseContext, "failed",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Website>, response: Response<Website>) {
                    adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recyclerview_source.adapter = adapter
                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))
                    swipe_refresh.isRefreshing = false

                }
            })

        }

    }
}
