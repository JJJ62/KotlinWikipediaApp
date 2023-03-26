package com.demo.code.ui.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.demo.code.data.DataStoreRepository
import com.demo.code.data.Repository
import com.demo.code.data.database.ArticleOfDayEntity
import com.demo.code.data.database.ArticlesEntity
import com.demo.code.data.database.SearchHistoryEntity
import com.demo.code.models.Article
import com.demo.code.models.ArticleOfDay
import com.demo.code.models.ArticleOfDayResult
import com.demo.code.models.QueryResult
import com.demo.code.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
): AndroidViewModel(application) {
    val articleOfDayEntity: LiveData<ArticleOfDayEntity> = repository.local.readArticleOfDay().asLiveData()

    var articleOfDayResponse : MutableLiveData<NetworkResult<ArticleOfDayResult>> = MutableLiveData()

    @SuppressLint("NewApi")
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun handleArticleOfDayResponse(response: Response<ArticleOfDayResult>): NetworkResult<ArticleOfDayResult>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("Error 402")
            }
            response.body()!!.articleOfDay == null -> {
                return NetworkResult.Error("Article of the day not found.")
            }
            response.isSuccessful -> {
                val articles = response.body()
                return NetworkResult.Success(articles!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    fun getArticleOfDay() = viewModelScope.launch {
        getArticleOfDayResult()
    }

    private suspend fun getArticleOfDayResult() {
        articleOfDayResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getArticleOfTheDay()
                articleOfDayResponse.value = handleArticleOfDayResponse(response)

                val articleOfDayR = articleOfDayResponse.value!!.data
                if(articleOfDayR != null) {
                    offlineCacheArticles(articleOfDayR)
                }
            } catch (e: Exception) {
                articleOfDayResponse.value = NetworkResult.Error("Article of the day not found.")
            }
        } else {
            articleOfDayResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun insertArticleOfDay(articleOfDayEntity: ArticleOfDayEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertArticleOfDay(articleOfDayEntity)
    }

    public fun insertSearchQuery(searchQuery:String) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertSearchHistory(SearchHistoryEntity(searchQuery))
    }

    private fun offlineCacheArticles(articleOfDayResult: ArticleOfDayResult) {
        val articleOfDayEntity = ArticleOfDayEntity(articleOfDayResult)
        insertArticleOfDay(articleOfDayEntity)
    }
}