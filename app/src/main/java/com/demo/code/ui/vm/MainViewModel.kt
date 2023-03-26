package com.demo.code.ui.vm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.demo.code.data.DataStoreRepository
import com.demo.code.data.Repository
import com.demo.code.data.database.ArticlesEntity
import com.demo.code.data.database.FavoriteArticlesEntity
import com.demo.code.models.Article
import com.demo.code.models.QueryResult
import com.demo.code.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
    dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    /** ROOM DATABASE */
    val readArticles: LiveData<List<ArticlesEntity>> = repository.local.readDatabase().asLiveData()

    /** RETROFIT */
    var articlesResponse: MutableLiveData<NetworkResult<QueryResult>> = MutableLiveData()

    private fun insertArticles(articlesEntity: ArticlesEntity) = viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertArticles(articlesEntity)
    }

    fun insertFavoriteArticles(favoriteArticlesEntity: FavoriteArticlesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertFavoriteArticles(favoriteArticlesEntity)
    }

    fun getArticles(searchQuery: String) = viewModelScope.launch {
        getArticlesSafeCall(searchQuery)
    }

    private suspend fun getArticlesSafeCall(searchQuery: String) {
        articlesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getArticles(searchQuery)
                //case

                articlesResponse.value = handleArticlesResponse(response)

                val article = articlesResponse.value!!.data
                if(article != null) {
                    offlineCacheArticles(article)
                }
            } catch (e: Exception) {
                articlesResponse.value = NetworkResult.Error("Articles not found.")
            }
        } else {
            articlesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheArticles(queryResult: QueryResult) {
        val articlesEntity = ArticlesEntity(queryResult)
        insertArticles(articlesEntity)
    }

    private fun handleArticlesResponse(response: Response<QueryResult>): NetworkResult<QueryResult>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.search.isEmpty() -> {
                return NetworkResult.Error("Articles not found.")
            }
            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


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
}