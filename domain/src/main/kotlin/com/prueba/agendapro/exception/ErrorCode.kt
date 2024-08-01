package com.prueba.agendapro.exception

enum class ErrorCode(val code: Int, val messageKey: String) {
    product_exist(409, "Product exist"),
    product_not_found(404, "Product not found"),
    error_delete_product(406, "Error delete Product"),
}
