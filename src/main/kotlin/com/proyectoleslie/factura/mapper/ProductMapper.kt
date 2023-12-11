package com.proyectoleslie.factura.mapper

import com.proyectoleslie.factura.dto.ProductDto
import com.proyectoleslie.factura.model.Product

object ProductMapper {
    fun mapToDto(product: Product): ProductDto {

        return ProductDto(
            product.id,
            "${product.description} ${product.brand}"
        )
    }
}