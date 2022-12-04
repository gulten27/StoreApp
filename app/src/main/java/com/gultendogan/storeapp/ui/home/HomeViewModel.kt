package com.gultendogan.storeapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.gultendogan.storeapp.data.api.ApiFactory
import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.data.entity.Products
import com.gultendogan.storeapp.data.local.StoreDao
import com.gultendogan.storeapp.domain.mapper.StoreEntityMapper
import com.gultendogan.storeapp.repository.StoreRepository
import com.gultendogan.storeapp.repository.HomeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl,
    private val apiFactory: ApiFactory,
    private val dbRepository: StoreRepository
): ViewModel() {

    val productList: MutableLiveData<List<Products>> = MutableLiveData()
    var roomList: MutableList<Products> = mutableListOf()
    fun getData(
    ) = viewModelScope.launch(Dispatchers.IO){
        productList.postValue(repository.getProducts())
        roomList.addAll(repository.getProducts())
        isFav(roomList,dbRepository.getAllFavorites())
    }

    fun addProduct(product: Products){//mapperla kullan
        viewModelScope.launch {
            dbRepository.addProduct(ProductEntity(title = product.title, price = product.price,
                category = product.category, description = product.description, image = product.image, isFav = product.isFav))
        }
    }

    fun addFavorite(product: Products){
        viewModelScope.launch {
            dbRepository.addFavorite(ProductEntity(title = product.title, price = product.price,
                category = product.category, description = product.description, image = product.image, isFav = product.isFav))
        }
    }

    fun deleteFavorite(product: Products){
        viewModelScope.launch(Dispatchers.IO) {
            var favList: MutableList<ProductEntity> = mutableListOf()
            favList.addAll(dbRepository.getAllFavorites())
            favList.forEach {
                if (it.title.equals(product.title)){
                    dbRepository.deleteFavorite(it.uid)
                }
            }
        }
    }

    fun isFav(products: List<Products>,favList: List<ProductEntity>){
        favList.forEach { fav->
            products.forEach { product->
                if (fav.title.equals(product.title)){
                    product.isFav = true
                }
            }
        }
        productList.postValue(products)
    }
}