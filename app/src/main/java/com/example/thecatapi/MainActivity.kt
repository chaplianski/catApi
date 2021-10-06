package com.example.thecatapi

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.adapter.PagingCatAdapter
import com.example.thecatapi.viewmodel.MainViewModel
import com.example.thecatapi.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainListAdapter: PagingCatAdapter
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingCatAdapter { null }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupList()
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.flow.collect(adapter::submitData)
        }
    }

    private fun setupList() {
        val viewModel = MainViewModel(APIService.getApiService())
        mainListAdapter = PagingCatAdapter() {
            val i = Intent(this@MainActivity, CatImage::class.java)

            i.putExtra("url", it)
            startActivity(i)
            overridePendingTransition(R.anim.grow_from_middle, R.anim.shrink_to_middle)
        }

        val rv: RecyclerView = findViewById(R.id.rv_cat)
        rv.apply {
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = GridLayoutManager(context, 2)
            }
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(context, 4)
            }

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
}
