package com.demo.code.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.demo.code.R
import com.demo.code.data.database.ArticleOfDayEntity
import com.demo.code.data.database.ArticlesEntity
import com.demo.code.databinding.FragmentHomeBinding
import com.demo.code.models.ArticleResult
import com.demo.code.ui.vm.MainViewModel
import com.demo.code.util.NetworkResult
import com.demo.code.util.observeOnce
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding !!

    //late init article of day
    private lateinit var mArticleOfDay: ArticleOfDayEntity

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDatabase()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            viewModel.articleOfDayEntity.observeOnce(viewLifecycleOwner) { database ->
                if (database != null) {
                    setUIData(database)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun setUIData(articleOfDayEntity: ArticleOfDayEntity){
        //convert article of day to capital case

        binding?.titleTextView?.text = articleOfDayEntity.articleOfDayResult.articleOfDay?.title.toString().replace("_", " ")
        //set timestamp
//        binding?.timestampTextView?.text = mArticleOfDay.articleOfDayResult.articleOfDay?.timestamp
        //set extract
        binding?.contentTextView?.text = articleOfDayEntity.articleOfDayResult.articleOfDay?.extract

        //detect when the user is done with entering data into the search input
        binding?.searchBar?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //insert value to database
                    viewModel.insertSearchQuery(query)
                    val action = HomeFragmentDirections.actionHomeFragmentToArticlesFragment(query)
                    binding?.root?.findNavController()?.navigate(action)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        });
    }

    private fun requestApiData() {
        viewModel.apply {
            getArticleOfDay()
            articleOfDayResponse.observe(viewLifecycleOwner, { response ->
                when(response){
                    is NetworkResult.Success -> {
                        response.data?.let {
//                            setUIData()
                            //save to database
                        }
                    }
                    is NetworkResult.Error -> {
//                        toast error in snackbar
                        Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading ->{
                        //diplay loading snackbar
                        Snackbar.make(requireView(), "Loading...", Snackbar.LENGTH_LONG).show()
                    }
                    else -> {
                        //do nothing
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}