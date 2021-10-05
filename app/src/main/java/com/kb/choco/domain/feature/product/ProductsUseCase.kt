package com.kb.choco.domain.feature.product

import com.kb.choco.domain.common.BaseUseCase
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.network.ProductApi
import com.kb.choco.util.extensions.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val api: ProductApi
) : BaseUseCase<ProductsUseCase.Params, List<ProductEntity>>() {

    override fun onBuild(params: Params): Flow<List<ProductEntity>> {
        return flowSingle { api.getProducts(token = params.token) }
    }

    data class Params(
        val token: String
    )
}