package com.kb.choco.ui.orderdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kb.choco.data.db.RoomRepository
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val repository: RoomRepository
) : BaseViewModel() {

    var productsList = MutableLiveData<List<ProductEntity>>()

    fun getProductsInOrder(orderID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductsFromOrder(orderID)
                .catch { onError(it) }
                .collect { productsList.postValue(it.products) }
        }
    }

    private fun onError(t: Throwable) {
        isError.postValue(true)
    }
}