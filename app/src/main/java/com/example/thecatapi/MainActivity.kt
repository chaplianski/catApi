package com.example.thecatapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.adapter.PagingCatAdapter
import com.example.thecatapi.model.Cat
import com.example.thecatapi.viewmodel.MainViewModel
import com.example.thecatapi.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


class MainActivity : AppCompatActivity() {

  //  private var mApiService: APIService? = null
  //  private var mAdapter: CatsAdapter? = null
//    private var cats: MutableList<Cat> = ArrayList()

 //   var catsList = mutableListOf<Cat>()

    lateinit var viewModel: MainViewModel
    lateinit var mainListAdapter: PagingCatAdapter
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingCatAdapter(){null}
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val adapter: CatsAdapter? = null
/*
        mApiService = RestClient.client.create(APIService::class.java)

        mAdapter = CatsAdapter(this, cats)
        val rv: RecyclerView = findViewById(R.id.rv_cat)
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = mAdapter
        catClickItems(cats, mAdapter!!)
        fetchCatList() */

        setupViewModel()
        setupList()
        setupView()


     /*   fun catClickItems(adapter: CatsAdapter) {
            adapter.setOnClickDogListener(object : CatsAdapter.onClickCatListener{
                override fun onItemClick(position: Int) {
                    val i = Intent (this, CatImageActivity::class.java)
                    i.putExtra("position", catsClickedList[position].id)

                    startActivity(i)
                }
            })


        }*/

    }


    private fun setupView() {
        lifecycleScope.launch {
            viewModel.flow.collect (adapter::submitData)

        }
    }

    private fun setupList() {
        val viewModel = MainViewModel(APIService.getApiService())
        mainListAdapter = PagingCatAdapter()
        {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        val rv: RecyclerView = findViewById(R.id.rv_cat)
        rv.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = mainListAdapter

            lifecycleScope.launch {
                viewModel.flow.collectLatest { pagingData ->
                    mainListAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(APIService.getApiService())
            )[MainViewModel::class.java]
    }


/*

    private fun fetchCatList(): List<Cat> {

        val call = mApiService?.fetchCats()
    //    var cats: List<Cat> = ArrayList()
        call?.enqueue(object: retrofit2.Callback <List<Cat>> {

           override fun onResponse (call: Call <List<Cat>>, response: Response <List<Cat>>) {
                val catResponse = response.body()
                Log.d("MyLog", "${response.body()}")

                if (catResponse == null){
                    mAdapter!!.notifyDataSetChanged()
                }
               if (catResponse != null) {
                   cats.addAll(catResponse)
                   Log.d("MyLog", "${cats}")
                   mAdapter!!.notifyDataSetChanged()

               }
            }
            override fun onFailure (call : Call<List<Cat>>, t: Throwable){

                Log.d("MyLog", "Error: ${t.localizedMessage}")
            }
        })
            return cats
    }




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
 //   }

*/




}