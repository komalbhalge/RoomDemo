package com.kb.choco.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_products", indices = [Index(value = ["id"], unique = true)])
data class ProductEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "productId") val productId: Int = 0,

    @SerializedName("Id")
    @ColumnInfo(name = "id") val id: String,

    @SerializedName("name")
    @ColumnInfo(name = "name") val name: String,

    @SerializedName("Description")
    @ColumnInfo(name = "dedscription") val description: String,

    @SerializedName("price")
    @ColumnInfo(name = "price") val price: Double,

    @SerializedName("photo")
    @ColumnInfo(name = "photo") val photo: String,

    @ColumnInfo(name = "orderId") var orderId: Long,

    var selected: Boolean? = false
)
