package com.prueba.agendapro.builder

import com.prueba.agendapro.entity.ProductUpdate

class ProductUpdateBuilder(
    private var sku: String? = null,
    private var name: String? = null,
    private var price: Double? = null,
    private var category: String? = null,
) {
    fun sku(sku: String?) = apply { this.sku = sku }
    fun name(name: String?) = apply { this.name = name }
    fun price(price: Double?) = apply { this.price = price }
    fun category(category: String?) = apply { this.category = category }

    fun build() = ProductUpdate(sku, name, price, category)
}
