package com.demo.code.ui.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.demo.code.data.Repository
import com.demo.code.data.database.ArticlesEntity
import com.demo.code.data.database.SearchHistoryEntity
import com.demo.code.models.QueryResult
import com.demo.code.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
): AndroidViewModel(application) {
    /** ROOM DATABASE */
    val readSearchHistory: LiveData<List<SearchHistoryEntity>> = repository.local.readSearchHistory().asLiveData()

    /** RETROFIT */
//    var articlesResponse: MutableLiveData<NetworkResult<QueryResult>> = MutableLiveData()

    private fun insertArticles(articlesEntity: ArticlesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertArticles(articlesEntity)
    }

//    fun getArticles(searchQuery: String) = viewModelScope.launch {
//        getArticlesSafeCall(searchQuery)
//    }

//    private suspend fun getArticlesSafeCall(searchQuery: String) {
//        //fetch search history from database
//        //if search history is not empty, then check if the search query is already in the database
//        //if it is, then update the search history
//
//        articlesResponse.value = NetworkResult.Loading()
//        if (hasInternetConnection()) {
//            try {
//                val response = repository.remote.getArticles(searchQuery)
//
//                articlesResponse.value = handleArticlesResponse(response)
//
//                val article = articlesResponse.value!!.data
//                if(article != null) {
//                    offlineCacheArticles(article)
//                }
//            } catch (e: Exception) {
//                articlesResponse.value = NetworkResult.Error("Articles not found.")
//            }
//        } else {
//            articlesResponse.value = NetworkResult.Error("No Internet Connection.")
//        }
//    }

//    private fun offlineCacheArticles(queryResult: QueryResult) {
//        val articlesEntity = ArticlesEntity(queryResult)
//        insertArticles(articlesEntity)
//    }

//    private fun handleArticlesResponse(response: Response<QueryResult>): NetworkResult<QueryResult>? {
//        when {
//            response.message().toString().contains("timeout") -> {
//                return NetworkResult.Error("Timeout")
//            }
//            response.code() == 402 -> {
//                return NetworkResult.Error("API Key Limited.")
//            }
//            response.body()!!.search.isEmpty() -> {
//                return NetworkResult.Error("Articles not found.")
//            }
//            response.isSuccessful -> {
//                val articles = response.body()
//                return NetworkResult.Success(articles!!)
//            }
//            else -> {
//                return NetworkResult.Error(response.message())
//            }
//        }
//    }


//    @SuppressLint("NewApi")
//    private fun hasInternetConnection(): Boolean {
//        val connectivityManager = getApplication<Application>().getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }
}