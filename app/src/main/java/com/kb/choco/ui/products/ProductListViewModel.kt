package com.kb.choco.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kb.choco.data.db.RoomRepository
import com.kb.choco.data.db.entities.OrderEntity
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.domain.feature.product.ProductsUseCase
import com.kb.choco.ui.common.BaseViewModel
import com.kb.choco.util.extensions.launchWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase,
    private val repository: RoomRepository
) : BaseViewModel() {

    var productsList = MutableLiveData<List<ProductEntity>>()

    val loginError = MutableLiveData<String>()

    fun getProductsList(token: String) {

        productsUseCase.build(
            ProductsUseCase.Params(token = token)
        )
            .onEach {
                addAllProductsToDbAndReturnFromDB(it)
            }
            .launchWith(this, onError = { onError(it) })
    }

    fun insertSelectedProducts(list: List<ProductEntity>) {
        val date = Date(Calendar.getInstance().getTime().getTime())
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertOrder(
                OrderEntity(order_date = date),
                list
            )
        }

    }

    private fun addAllProductsToDbAndReturnFromDB(list: List<ProductEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (product in list) {
                repository.insertProduct(product)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts()
                .catch { e -> onError(e) }
                .collect { productsList.postValue(it) }
        }

    }

    private fun onError(t: Throwable) {
        loginError.postValue(t.message)
    }

}