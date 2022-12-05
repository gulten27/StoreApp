package com.gultendogan.storeapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.gultendogan.storeapp.data.api.ApiFactory
import com.gultendogan.storeapp.data.entity.Categories
import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.data.entity.Products
import com.gultendogan.storeapp.domain.mapper.ProductEntityMapper
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
    private val dbRepository: StoreRepository
): ViewModel() {
    val categoryList: MutableLiveData<Categories> = MutableLiveData()
    val productList: MutableLiveData<List<Products>> = MutableLiveData()
    private var roomList: MutableLiveData<List<Products>> = MutableLiveData()
    private var filteredProductList: MutableList<Products> = mutableListOf()
    val progressBar = MutableLiveData<Boolean>()
    val mapper = ProductEntityMapper()
    fun getData(
    ) = viewModelScope.launch(Dispatchers.IO){
        progressBar.postValue(true)
        categoryList.postValue(repository.getCategories())
        productList.postValue(repository.getProducts())
        roomList.postValue(repository.getProducts())
        roomList.value?.let { isFav(it,dbRepository.getAllFavorites()) }
    }

    fun addCart(product: Products){
        viewModelScope.launch {
            val productEntity: ProductEntity = mapper.mapToEntity(product)
            dbRepository.addProduct(productEntity)
        }
    }

    fun addFavorite(product: Products){
        viewModelScope.launch {
            val productEntity: ProductEntity = mapper.mapToEntity(product)
            dbRepository.addFavorite(productEntity)
        }
    }

    fun deleteFavorite(product: Products){
        viewModelScope.launch(Dispatchers.IO) {
            val favList: MutableList<ProductEntity> = mutableListOf()
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
        progressBar.postValue(false)
    }

    fun getCategoryProduct(category: String){
        progressBar.postValue(true)
        filteredProductList.clear()
        roomList.value?.forEach {
            if (it.category.equals(category)){
                filteredProductList.add(it)
            }
        }
        roomList.value = filteredProductList
    }
}