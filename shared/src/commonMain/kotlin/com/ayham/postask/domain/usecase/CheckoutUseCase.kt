package com.ayham.postask.domain.usecase

import com.ayham.postask.domain.model.OrderLineModel
import com.ayham.postask.domain.model.OrderModel
import com.ayham.postask.domain.repository.CartRepository
import com.ayham.postask.domain.repository.OrderRepository
import com.ayham.postask.util.ResultWrapper
import kotlinx.coroutines.flow.last
import kotlin.time.Clock
import kotlin.uuid.Uuid

class CheckoutUseCase(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val calculateTotals: CalculateCartTotalsUseCase,
) {
    suspend operator fun invoke(): ResultWrapper<OrderModel> {
        val items = cartRepository.items.value
        val order = OrderModel(
            id = Uuid.random().toString(),
            createdAtEpochMillis = Clock.System.now().toEpochMilliseconds(),
            syncedAtEpochMillis = null,
            totals = calculateTotals(items),
            lines = items.map { item ->
                OrderLineModel(
                    productId = item.product.id,
                    productName = item.product.name,
                    unitPriceCents = item.product.priceCents,
                    quantity = item.quantity,
                    taxable = item.product.taxable,
                )
            },
        )
        return when (val result = orderRepository.saveOrder(order).last()) {
            is ResultWrapper.Success -> {
                cartRepository.clear()
                ResultWrapper.Success(order)
            }
            is ResultWrapper.Error -> result
            ResultWrapper.Loading -> ResultWrapper.Error(IllegalStateException("checkout did not complete"))
        }
    }
}
