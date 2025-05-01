package com.akakce.case_study.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.akakce.case_study.R
import com.akakce.case_study.databinding.FragmentProductDetailBinding
import com.akakce.case_study.ui.viewmodel.ProductDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var productId: Int = -1
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductDetailViewModel by viewModels()

    companion object {
        private const val ARG_PRODUCT_ID = "product_id"

        fun newInstance(productId: Int): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val args = Bundle()
            args.putInt(ARG_PRODUCT_ID, productId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt(ARG_PRODUCT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.fetchProductDetail(productId)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.productDetail.observe(viewLifecycleOwner) { result ->
            result.onSuccess { productDetail ->

                Glide.with(requireContext())
                    .load(productDetail.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.imageViewProduct)

                binding.textViewTitle.text = productDetail.title
                binding.textViewPrice.text = "$${productDetail.price}"
                binding.textViewCategory.text = "Kategori: ${productDetail.category}"
                binding.textViewDescription.text = productDetail.description

                binding.ratingBar.rating = productDetail.rating.rate.toFloat()
                binding.textViewRatingCount.text = "${productDetail.rating.count} oy"
            }.onFailure {
                binding.contentScrollView.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.contentScrollView.visibility = if (isLoading) View.GONE else View.VISIBLE
            binding.errorView.visibility = View.GONE
        }
    }

}
