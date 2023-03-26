package com.demo.code.ui.view

import android.app.Application
import androidx.lifecycle.*
import com.demo.code.data.Repository
import com.demo.code.data.database.ArticlesEntity
import com.demo.code.data.database.FavoriteArticlesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
): AndroidViewModel(application){
    /** ROOM DATABASE */
    val readFavoriteArticles: LiveData<List<FavoriteArticlesEntity>> = repository.local.readFavoriteArticles().asLiveData()

    /** RETROFIT */
//    var articlesResponse: MutableLiveData<NetworkResult<QueryResult>> = MutableLiveData()

    fun insertFavoriteArticles(favoriteArticlesEntity: FavoriteArticlesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertFavoriteArticles(favoriteArticlesEntity)
    }

//    fun getArticles(searchQuery: String) = viewModelScope.launch {
//        getArticlesSafeCall(searchQuery)
//    }

//    private suspend fun getArticlesSafeCall(searchQuery: String) {
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