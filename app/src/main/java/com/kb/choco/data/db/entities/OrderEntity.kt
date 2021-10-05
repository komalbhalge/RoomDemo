package com.kb.choco.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "table_orders")
class OrderEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "orderId") val orderId: Int = 0,
    @ColumnInfo(name = "orderDate") val order_date: Date? = null
)