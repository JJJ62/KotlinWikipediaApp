package com.demo.code.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.code.adapters.ArticlesAdapter
import com.demo.code.adapters.SearchHistoryAdapter
import com.demo.code.databinding.FragmentArticlesBinding
import com.demo.code.databinding.FragmentSearchHistoryBinding
import com.demo.code.ui.vm.MainViewModel
import com.demo.code.util.NetworkResult
import com.demo.code.util.observeOnce
import kotlinx.coroutines.launch

class SearchHistoryFragment : Fragment() {

    // View Binding
    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!

    // View Model
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel

    // List view adapter
    private val mAdapter by lazy { SearchHistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchHistoryViewModel = ViewModelProvider(requireActivity())[SearchHistoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        //get search query from bundle
//        val args = arguments
//        val query = args?.getString("query")
//        if (query != null) {
//            readDatabase(query)
//        }
        readDatabase()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            searchHistoryViewModel.readSearchHistory.observeOnce(viewLifecycleOwner) { database ->
//                requestApiData(searchQuery)
                if (database.isNotEmpty()) {
                    //caste database to sstring list
                    val searchHistoryList = database.map { it.searchedString }
                    mAdapter.setData(searchHistoryList)
                    displayView()
//                    displayShimmerView(displayShimmerView = false)
                }
//                else {
//                    requestApiData(searchQuery)
//                }
            }
        }
    }

//    private fun requestApiData(searchQuery: String) {
//        searchHistoryViewModel.apply {
//            getArticles(searchQuery)
//            articlesResponse.observe(viewLifecycleOwner) { response ->
//                when (response) {
//                    is NetworkResult.Success -> {
//                        displayShimmerView(displayShimmerView = false)
//                        response.data?.let {
//                            mAdapter.setData(it)
//                            displayView()
//                        }
//                    }
//                    is NetworkResult.Error -> {
//                        displayShimmerView(displayShimmerView = false)
//                        noConnectivityErrorView(response.message.toString())
//                    }
//                    is NetworkResult.Loading -> {
//                        displayShimmerView(displayShimmerView = true)
//                    }
//                    else -> {
//                        displayShimmerView(displayShimmerView = false)
//                    }
//                }
//            }
//        }
//    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerview.adapter = mAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
        }
//        displayShimmerView(displayShimmerView = true)
    }

//    private fun displayShimmerView(displayShimmerView :Boolean) = binding.apply {
//        if (displayShimmerView) recyclerview.showShimmer() else recyclerview.hideShimmer()
//    }

//    private fun noConnectivityErrorView(message: String) = binding.apply {
//        errorTextView.text = message
//        errorTextView.visibility = View.VISIBLE
//        errorImageView.visibility = View.VISIBLE
//        recyclerview.visibility = View.GONE
//    }

    private fun displayView() = binding.apply {
        errorTextView.visibility = View.GONE
        errorImageView.visibility = View.GONE
        recyclerview.visibility = View.VISIBLE
    }


}