package com.kb.choco.data.db

import androidx.room.*
import com.kb.choco.data.db.entities.OrderEntity
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.data.db.relations.OrderProductCrosRef
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM table_products")
    fun getProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert
    suspend fun addNewOrder(order: OrderEntity): Long

    @Insert
    suspend fun addProductsList(newItems: List<ProductEntity>)

    @Query("SELECT * FROM table_orders")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM table_orders WHERE orderId = :orderID")
    fun getProductsFromOrder(orderID: Int): Flow<OrderWithProducts>

    @Transaction
    suspend fun insertOrUpdateOrder(order: OrderEntity, products: List<ProductEntity>) {
        val orderId = addNewOrder(order)
        for (product in products) {
            product.orderId = orderId
            insertOrderProductCrossRef(
                OrderProductCrosRef(productId = product.productId, orderId = orderId.toInt())
            )
        }

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderProductCrossRef(crossRef: OrderProductCrosRef)

}