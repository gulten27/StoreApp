package com.gultendogan.storeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.gultendogan.storeapp.data.ApiFactory
import com.gultendogan.storeapp.data.Products
import com.gultendogan.storeapp.repository.HomeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl,
    private val apiFactory: ApiFactory
): ViewModel() {

    val characterList: MutableLiveData<List<Products>> = MutableLiveData()

    fun getData(
    ) = viewModelScope.launch(Dispatchers.IO){
        characterList.postValue(repository.getProducts())
    }
}