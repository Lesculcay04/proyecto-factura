package com.proyectoleslie.factura.repository

import com.proyectoleslie.factura.model.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository: JpaRepository<Invoice, Long?> {
    fun findById (id: Long?): Invoice?

}