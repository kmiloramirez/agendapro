package com.prueba.agendapro.builder

import com.prueba.agendapro.utilobjects.Pageable

class PageableBuilder(
    private var search: String? = null,
    private var page: Int = 0,
    private var size: Int = 10,
) {
    fun search(search: String) = apply { this.search = search }
    fun page(page: Int) = apply { this.page = page }
    fun size(size: Int) = apply { this.size = size }
    fun build() = Pageable(search, page, size)
}
