package com.kb.choco.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kb.choco.data.db.RoomRepository
import com.kb.choco.data.db.entities.OrderEntity
import com.kb.choco.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val repository: RoomRepository
) : BaseViewModel() {

    var orderList = MutableLiveData<List<OrderEntity>>()

    fun getOrderList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllOrders()
                .catch { onError(it) }
                .collect { orderList.postValue(it) }
        }

    }

    private fun onError(t: Throwable) {
        isError.postValue(true)
    }
}