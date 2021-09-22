package com.example.thecatapi

import android.app.LauncherActivity
import android.content.Intent
import android.graphics.Movie
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.internal.notifyAll
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.create
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private var mApiService: APIService? = null
    private var mAdapter: CatsAdapter? = null
    private var cats: MutableList<Cat> = ArrayList()

    var catsList = mutableListOf<Cat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val adapter: CatsAdapter? = null

        mApiService = RestClient.client.create(APIService::class.java)

        mAdapter = CatsAdapter(this, cats)
        val rv: RecyclerView = findViewById(R.id.rv_cat)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = mAdapter

        fetchCatList()
      }

    private fun fetchCatList() {




        val call = mApiService?.fetchCats()

        call?.enqueue(object: retrofit2.Callback <List<Cat>> {

            val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)



            override fun onResponse (call: Call <List<Cat>>, response: Response<List<Cat>>){
                val catResponse = response.body()
                Log.d("MyLog", "${response.body()}")
                if (catResponse == null){
                    mAdapter!!.notifyDataSetChanged()
                }
            }
            override fun onFailure (call : Call<List<Cat>>, t: Throwable){

                Log.d("MyLog", "Error: ${t.localizedMessage}")
            }
        })
    }



  /*  private fun getCats() {

        val adapter = CatsAdapter(this, catsList)
        val catsService = GetCatsService()
        catsService.cats {
          catsList.map { result -> Cat (result.id, result.imageUrl) } as MutableList<Cat>
            val rv: RecyclerView = findViewById(R.id.rv_cat)
            rv.layoutManager = LinearLayoutManager(this)
            rv.adapter = adapter
            }
        }*/
    }



/*    private fun catClickItems(catsClickedList: List<Cat>, adapter: CatsAdapter) {
        adapter.setOnClickDogListener(object : CatsAdapter.onClickCatListener{
            override fun onItemClick(position: Int) {
                val i = Intent(this, CatImageActivity::class.java)
                i.putExtra("position", catsClickedList[position].id)

                startActivity(i)
            }
        })


    }*/


//}