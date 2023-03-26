package com.demo.code.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.code.databinding.ArticlesRowLayoutBinding
import com.demo.code.databinding.FavoriteArticlesRowLayoutBinding
import com.demo.code.models.Article
import com.demo.code.models.FavoriteArticles
import com.demo.code.models.QueryResult
import com.demo.code.ui.view.ArticlesFragmentDirections
import com.demo.code.ui.view.FavoriteArticlesFragmentDirections
import com.demo.code.util.ArticlesDiffUtil
import com.demo.code.util.FavoriteArticlesDiffUtil

class FavoriteArticlesAdapter () : RecyclerView.Adapter<FavoriteArticlesAdapter.ViewHolder>() {

    var favoriteArticlesList: List<FavoriteArticles> = arrayListOf()

    inner class ViewHolder(val binding: FavoriteArticlesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavoriteArticlesRowLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(favoriteArticlesList[position]){
                binding.titleTextView.text = this.articleTitle
                binding.descriptionTextView.text = this.articleDescription
//                binding.starButton.setOnClickListener {
//                    if (binding.starButton.background.constantState == ContextCompat.getDrawable(binding.root.context, android.R.drawable.btn_star_big_on)?.constantState){
//                        binding.starButton.setBackgroundResource(android.R.drawable.btn_star_big_off)
//                    }else{
//                        binding.starButton.setBackgroundResource(android.R.drawable.btn_star_big_on)
//                    }
//                }
            }
            binding.root.setOnClickListener {
                setOnClick(it, favoriteArticlesList[position])
            }
        }
    }

    private fun setOnClick(view: View, favoriteArticle: FavoriteArticles){
        val article = Article(
            title = favoriteArticle.articleTitle,
            snippet = favoriteArticle.articleDescription,
            pageid = 0,
            ns = 0,
            extract = favoriteArticle.articleDescription
        )

        val action = FavoriteArticlesFragmentDirections.actionFavoriteArticlesFragmentToSingleArticleFragment(article)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return favoriteArticlesList.size
    }

    fun setData(newData: List<FavoriteArticles>){
        val articlesDiffUtil = FavoriteArticlesDiffUtil(favoriteArticlesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(articlesDiffUtil)
        favoriteArticlesList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}