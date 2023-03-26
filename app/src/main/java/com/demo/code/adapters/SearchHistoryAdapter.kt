package com.demo.code.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.code.databinding.ArticlesRowLayoutBinding
import com.demo.code.databinding.SearchHistoryRowLayoutBinding
import com.demo.code.models.Article
import com.demo.code.models.QueryResult
import com.demo.code.ui.view.ArticlesFragmentDirections
import com.demo.code.util.ArticlesDiffUtil
import com.demo.code.util.SearchHistoryDiffUtil

class SearchHistoryAdapter () : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {

    var searchHistoryList: List<String> = arrayListOf()

    inner class ViewHolder(val binding: SearchHistoryRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchHistoryRowLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(searchHistoryList[position]){
                binding.titleTextView.text = this
            }
//            binding.root.setOnClickListener {
//                setOnClick(it, searchHistoryList[position])
//            }
        }
    }

//    private fun setOnClick(view: View, item: String){
//        val action = ArticlesFragmentDirections.actionArticlesFragmentToSingleArticleFragment(item)
//        view.findNavController().navigate(action)
//    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    fun setData(newData: List<String>){
        val articlesDiffUtil = SearchHistoryDiffUtil(searchHistoryList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(articlesDiffUtil)
        searchHistoryList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}