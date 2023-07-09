package com.foodsoul.test.ui.image_search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodsoul.test.App
import com.foodsoul.test.data.image_search.model.ImageResponseModel
import com.foodsoul.test.database.image_search.dao.SuggestionsDao
import com.foodsoul.test.database.image_search.model.Suggestion
import com.foodsoul.test.network.image_search.ImageSearchApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesSearchViewModel : ViewModel() {

    @Inject
    lateinit var imageSearchApi: ImageSearchApi

    @Inject
    lateinit var suggestionsDao: SuggestionsDao

    init {
        App.INSTANCE.appComponent.inject(this)
    }

    private val imagesMutableLiveData: MutableLiveData<ImageResponseModel> = MutableLiveData<ImageResponseModel>()
    private val suggestionsMutableLiveData: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()
    private var suggestionJob: Job? = null

    @SuppressLint("CheckResult")
    fun searchImages(query: String) {
        validateQuery(query)?.let {
        isLoading.value = true
        suggestionJob?.cancel()
        imageSearchApi.searchImages(it)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                imagesMutableLiveData.postValue(response)
                isLoading.postValue(false)
            },
                { error ->
                    errorMessage.postValue(error.message)
                    isLoading.postValue(false)
                })
        }
    }

    fun updateSuggestionsList(query: String) {
        suggestionJob = CoroutineScope(Dispatchers.Main).launch {
            validateQuery(query)?.let {
                val adapterItemsList: MutableList<String> = ArrayList()
                val suggestionsList = suggestionsDao.getAllItems()
                suggestionsList.map { suggestion ->
                    if (suggestion.text.startsWith(query.toLowerCase())) {
                        adapterItemsList.add(suggestion.text)
                    }
                }
                suggestionsMutableLiveData.postValue(adapterItemsList)
            }
        }
    }

    fun getImagesList(): LiveData<ImageResponseModel> {
        return imagesMutableLiveData
    }

    fun saveSuggestion(query: String) {
        validateQuery(query)?.let {
            suggestionsDao.insertItem(Suggestion(text = it.toLowerCase()))
        }
    }

    fun getSuggestions(): LiveData<List<String>> {
        return suggestionsMutableLiveData
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun getError(): MutableLiveData<String> {
        return errorMessage
    }

    private fun validateQuery(query: String): String? {
        return query.ifEmpty { null }
    }

}