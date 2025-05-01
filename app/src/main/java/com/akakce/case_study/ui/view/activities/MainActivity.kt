package com.akakce.case_study.ui.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.akakce.case_study.R
import com.akakce.case_study.adapter.ImageSliderAdapter
import com.akakce.case_study.adapter.ProductListAdapter
import com.akakce.case_study.data.model.Product
import com.akakce.case_study.databinding.ActivityMainBinding
import com.akakce.case_study.ui.view.fragment.ProductDetailFragment
import com.akakce.case_study.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        observeViewModel()

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {

        }
    }

    private fun observeViewModel() {
        viewModel.allProducts.observe(this, Observer { result ->
            result.onSuccess { productList ->
                if (productList.isNotEmpty()) {
                    setupRecyclerView(productList)
                    binding.productRecyclerView.visibility = View.VISIBLE
                } else {
                    binding.productRecyclerView.visibility = View.GONE
                }
            }
            result.onFailure {
                binding.scrollView.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
            }
        })
        viewModel.horizontalProducts.observe(this, Observer { result ->
            result.onSuccess { productList ->
                if (productList.isNotEmpty()) {
                    setupViewPager(productList)
                    binding.productsViewPager.visibility = View.VISIBLE
                } else {
                    binding.productsViewPager.visibility = View.GONE
                }
            }
            result.onFailure {
                binding.scrollView.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
            }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }

    private fun setupRecyclerView(products: List<Product>) {
        val adapter = ProductListAdapter(products) { selectedProduct ->
            val fragment = ProductDetailFragment.newInstance(selectedProduct.id)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()

        }
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.productRecyclerView.adapter = adapter
    }


    private fun setupViewPager(products: List<Product>) {
        val adapter = ImageSliderAdapter(products) { product ->
            val fragment = ProductDetailFragment.newInstance(product.id)

            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.productsViewPager.adapter = adapter
        adapter.notifyDataSetChanged()

        setupIndicator(products.size)
        binding.productsViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicator(position)
            }
        })
    }

    private fun setupIndicator(count: Int) {
        val indicatorLayout = binding.indicatorLayout
        indicatorLayout.removeAllViews()

        for (i in 0 until count) {
            val indicator = ImageView(this).apply {
                setImageResource(R.drawable.circle_indicator_unselected)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 0, 10, 0)
                layoutParams = params


                setOnClickListener {
                    binding.productsViewPager.currentItem = i
                }
            }
            indicatorLayout.addView(indicator)
        }

        updateIndicator(0)
    }


    private fun updateIndicator(position: Int) {
        val indicatorLayout = binding.indicatorLayout
        for ((index, view) in indicatorLayout.children.withIndex()) {
            if (view is ImageView) {
                view.setImageResource(if (index == position) R.drawable.rectangle_indicator_selected else R.drawable.circle_indicator_unselected)
            }
        }
    }


}
