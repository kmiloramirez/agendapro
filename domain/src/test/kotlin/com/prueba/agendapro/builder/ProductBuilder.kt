package com.prueba.agendapro.builder

import com.prueba.agendapro.entity.Product
import java.util.UUID

class ProductBuilder(
    private var id: UUID = UUID.randomUUID(),
    private var sku: String = "SKU-0001",
    private var name: String = "Test product name",
    private var price: Double = 10000.0,
    private var category: String = "Test category",
) {
    fun id(id: UUID) = apply { this.id = id }
    fun sku(sku: String) = apply { this.sku = sku }
    fun name(name: String) = apply { this.name = name }
    fun price(price: Double) = apply { this.price = price }
    fun category(category: String) = apply { this.category = category }

    fun build() = Product(id, sku, name, price, category)
}
