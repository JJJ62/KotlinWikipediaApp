package com.demo.code.util

import androidx.recyclerview.widget.DiffUtil
import com.demo.code.models.Article
import com.demo.code.models.FavoriteArticles

class FavoriteArticlesDiffUtil(
        private val oldList: List<FavoriteArticles>,
    private val newList: List<FavoriteArticles>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}