package com.gultendogan.storeapp.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dbRepository: StoreRepository
): ViewModel() {
    val favList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    val progressBar = MutableLiveData<Boolean>()
    fun getAllFavoriteFromRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            progressBar.postValue(true)
            favList.postValue(dbRepository.getAllFavorites())
        }
    }

    fun deleteFavorite(productId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteFavorite(productId)
            getAllFavoriteFromRoom()
        }
    }

}