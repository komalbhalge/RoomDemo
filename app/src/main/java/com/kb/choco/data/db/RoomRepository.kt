package com.kb.choco.data.db

import androidx.annotation.WorkerThread
import com.kb.choco.data.db.entities.OrderEntity
import com.kb.choco.data.db.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

    fun getProducts(): Flow<List<ProductEntity>> {
        return appDao.getProducts()
    }

    @WorkerThread
    suspend fun insertProduct(product: ProductEntity) {
        appDao.insertProduct(product)
    }

    fun getAllOrders(): Flow<List<OrderEntity>> {
        return appDao.getAllOrders()
    }

    fun getProductsFromOrder(orderId: Int): Flow<OrderWithProducts> {
        return appDao.getProductsFromOrder(orderId)
    }

    @WorkerThread
    suspend fun insertOrder(order: OrderEntity, products: List<ProductEntity>) {
        return appDao.insertOrUpdateOrder(order, products)
    }

}