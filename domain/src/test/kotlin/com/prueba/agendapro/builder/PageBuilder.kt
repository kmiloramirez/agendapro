package com.prueba.agendapro.builder

import com.prueba.agendapro.utilobjects.Page

class PageBuilder<T>(
    private var content: List<T> = emptyList(),
    private var totalPages: Int = 1,
    private var totalElements: Int = content.size,
    private var currentPage: Int = 0,
) {

    fun content(content: List<T>) = apply { this.content = content }
    fun totalPages(totalPages: Int) = apply { this.totalPages = totalPages }
    fun totalElements(totalElements: Int) = apply { this.totalElements = totalElements }
    fun currentPage(currentPage: Int) = apply { this.currentPage = currentPage }
    fun build() = Page(content, totalPages, totalElements, currentPage)
}
