package com.prueba.agendapro.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "products")
class ProductModel {
    @Id
    lateinit var id: UUID

    @Column
    lateinit var sku: String

    @Column
    lateinit var name: String

    @Column
    var price: Double = 0.0

    @Column
    lateinit var category: String
}
