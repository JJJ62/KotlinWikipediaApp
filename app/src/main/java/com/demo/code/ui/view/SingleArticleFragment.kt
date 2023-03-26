package com.demo.code.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.demo.code.R
import com.demo.code.databinding.FragmentSingleArticleBinding
import com.demo.code.models.Article
import com.demo.code.ui.vm.MainViewModel
import com.demo.code.util.NetworkResult
import com.demo.code.util.observeOnce
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SingleArticleFragment : Fragment() {
    private var _binding: FragmentSingleArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var article:Article

    private lateinit var singleArticleViewModel: SingleArticleViewModel

    companion object {
        fun newInstance() = SingleArticleFragment()
    }

//    private lateinit var viewModel: SingleArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get parcelable data from the bundle
        article = arguments?.getParcelable("article")!!
//        singleArticleViewModel = ViewModelProvider(this).get(SingleArticleViewModel::class.java)
        singleArticleViewModel = ViewModelProvider(requireActivity())[SingleArticleViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleArticleBinding.inflate(inflater, container, false)
        readDatabase(article.title!!)
        return binding.root
    }

    private fun readDatabase(searchQuery:String) {
        lifecycleScope.launch {
            singleArticleViewModel.readArticles.observeOnce(viewLifecycleOwner) { database ->
                requestApiData(searchQuery)
            }
        }
    }

    //create function to initialize the ui
    private fun initUi() {
        //set the title
        binding.singleArticleTitle.text = article.title
        //set the author
        binding.singleArticleText.text = article.snippet
    }

    private fun requestApiData(searchQuery: String) {
        singleArticleViewModel.apply {
            getArticles(searchQuery)
            articlesResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
//                        displayShimmerView(displayShimmerView = false)
                        response.data?.let {
                            article = it.pages[0]
                            binding.singleArticleTitle.text = it.pages[0].title
                            binding.singleArticleText.text = it.pages[0].extract

                        }
                    }
                    is NetworkResult.Error -> {
//                        displayShimmerView(displayShimmerView = false)
//                        noConnectivityErrorView(response.message.toString())
                        Snackbar.make(
                            binding.root,
                            "An error occurred: ${response.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is NetworkResult.Loading -> {
//                        displayShimmerView(displayShimmerView = true)
                        Snackbar.make(
                            binding.root,
                            "Loading...",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    else -> {
//                        displayShimmerView(displayShimmerView = false)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}