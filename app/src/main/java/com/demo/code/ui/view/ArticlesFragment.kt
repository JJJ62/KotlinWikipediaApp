package com.demo.code.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.code.adapters.ArticlesAdapter
import com.demo.code.data.database.FavoriteArticlesEntity
import com.demo.code.databinding.FragmentArticlesBinding
import com.demo.code.interfaces.AddFavoriteArticles
import com.demo.code.ui.vm.MainViewModel
import com.demo.code.util.NetworkResult
import com.demo.code.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment  : Fragment(), AddFavoriteArticles {

    // View Binding
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    // View Model
    private lateinit var mainViewModel: MainViewModel

    // List view adapter
    private val mAdapter by lazy { ArticlesAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        //get search query from bundle
        val args = arguments
        val query = args?.getString("query")
        if (query != null) {
            readDatabase(query)
        }
    }

    private fun readDatabase(searchQuery:String) {
        lifecycleScope.launch {
            mainViewModel.readArticles.observeOnce(viewLifecycleOwner) { database ->
                requestApiData(searchQuery)
//                if (database.isNotEmpty()) {
//                    mAdapter.setData(database[0].queryResult)
//                    displayShimmerView(displayShimmerView = false)
//                } else {
//                    requestApiData(searchQuery)
//                }
            }
        }
    }

    private fun requestApiData(searchQuery: String) {
        mainViewModel.apply {
            getArticles(searchQuery)
            articlesResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        displayShimmerView(displayShimmerView = false)
                        response.data?.let {
                            mAdapter.setData(it)
                            displayView()
                        }
                    }
                    is NetworkResult.Error -> {
                        displayShimmerView(displayShimmerView = false)
                        noConnectivityErrorView(response.message.toString())
                    }
                    is NetworkResult.Loading -> {
                        displayShimmerView(displayShimmerView = true)
                    }
                    else -> {
                        displayShimmerView(displayShimmerView = false)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerview.adapter = mAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
        }
        displayShimmerView(displayShimmerView = true)
    }

    private fun displayShimmerView(displayShimmerView :Boolean) = binding.apply {
        if (displayShimmerView) recyclerview.showShimmer() else recyclerview.hideShimmer()
    }

    private fun noConnectivityErrorView(message: String) = binding.apply {
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
        errorImageView.visibility = View.VISIBLE
        recyclerview.visibility = View.GONE
    }

    private fun displayView() = binding.apply {
        errorTextView.visibility = View.GONE
        errorImageView.visibility = View.GONE
        recyclerview.visibility = View.VISIBLE
    }

    override fun addFavoriteArticles(favoriteArticlesEntity: FavoriteArticlesEntity) {
//        TODO("Not yet implemented")
//        favoriteArticlesViewModel.insertFavoriteArticles(favoriteArticlesEntity);
        mainViewModel.insertFavoriteArticles(favoriteArticlesEntity);
    }


}