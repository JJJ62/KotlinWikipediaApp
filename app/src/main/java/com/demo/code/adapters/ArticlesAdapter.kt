package com.demo.code.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.code.R
import com.demo.code.data.database.ArticlesEntity
import com.demo.code.data.database.FavoriteArticlesEntity
import com.demo.code.databinding.ArticlesRowLayoutBinding
import com.demo.code.interfaces.AddFavoriteArticles
import com.demo.code.models.Article
import com.demo.code.models.FavoriteArticles
import com.demo.code.models.QueryResult
import com.demo.code.ui.view.ArticlesFragmentDirections
import com.demo.code.util.ArticlesDiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesAdapter constructor(
    var addFavoriteArticles: AddFavoriteArticles
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var articlesList: List<Article> = arrayListOf()

    inner class ViewHolder(val binding: ArticlesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticlesRowLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(articlesList[position]){
                binding.titleTextView.text = this.title
                binding.descriptionTextView.text = this.snippet
                binding.starButton.setOnClickListener {
                    if (binding.starButton.background.constantState == ContextCompat.getDrawable(
                            binding.root.context,
                            android.R.drawable.btn_star_big_on
                        )?.constantState
                    ) {
                        binding.starButton.setBackgroundResource(android.R.drawable.btn_star_big_off)
                    } else {
                        binding.starButton.setBackgroundResource(android.R.drawable.btn_star_big_on)
                    }
                    val favoriteArticlesEntity = FavoriteArticlesEntity(
                        favoriteArticles = FavoriteArticles(
                            articleTitle = this.title ?: "",
                            articleDescription = this.snippet ?: ""
                        )
                    )
                    addFavoriteArticles.addFavoriteArticles(favoriteArticlesEntity)
                }
            }
            binding.root.setOnClickListener {
                setOnClick(it, articlesList[position])
            }
        }
    }

    private fun setOnClick(view: View, article: Article){
        val action = ArticlesFragmentDirections.actionArticlesFragmentToSingleArticleFragment(article)
        view.findNavController().navigate(action)
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    fun setData(newData: QueryResult){
        val articlesDiffUtil = ArticlesDiffUtil(articlesList, newData.search)
        val diffUtilResult = DiffUtil.calculateDiff(articlesDiffUtil)
        articlesList = newData.search
        diffUtilResult.dispatchUpdatesTo(this)
    }
}