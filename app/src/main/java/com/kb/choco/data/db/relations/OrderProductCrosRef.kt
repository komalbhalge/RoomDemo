package com.kb.choco.data.db.relations

import androidx.room.Entity

@Entity(tableName = "OrderProductCrosRef",
    primaryKeys = ["productId", "orderId"])
data class OrderProductCrosRef(
    val productId: Int,
    val orderId: Int
)