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

/*
    fun FlipListener(front: View, back: View) {
        this.mFrontView = front
        this.mBackView = back
        this.mBackView.setVisibility(View.GONE)
    }

    fun onAnimationUpdate(animation: ValueAnimator) {
        val value = animation.animatedFraction
        val scaleValue = 0.625f + 1.5f * (value - 0.5f) * (value - 0.5f)
        if (value <= 0.5f) {
            this.mFrontView.setRotationY(180 * value)
            this.mFrontView.setScaleX(scaleValue)
            this.mFrontView.setScaleY(scaleValue)
            if (mFlipped) {
                setStateFlipped(false)
            }
        } else {
            this.mBackView.setRotationY(-180 * (1f - value))
            this.mBackView.setScaleX(scaleValue)
            this.mBackView.setScaleY(scaleValue)
            if (!mFlipped) {
                setStateFlipped(true)
            }
        }
    }

    private fun setStateFlipped(flipped: Boolean) {
        mFlipped = flipped
        this.mFrontView.setVisibility(if (flipped) View.GONE else View.VISIBLE)
        this.mBackView.setVisibility(if (flipped) View.VISIBLE else View.GONE)
    }
}*/
}
