package com.kb.choco.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kb.choco.data.db.entities.OrderEntity
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.data.db.relations.OrderProductCrosRef

data class OrderWithProducts (
    @Embedded
    val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "productId",
        associateBy = Junction(OrderProductCrosRef::class)
    )
    val products: List<ProductEntity>
)