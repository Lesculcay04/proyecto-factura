package com.proyectoleslie.factura.service

import com.proyectoleslie.factura.model.Detail
import com.proyectoleslie.factura.repository.DetailRepository
import com.proyectoleslie.factura.repository.InvoiceRepository
import com.proyectoleslie.factura.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DetailService {
    @Autowired
    lateinit var invoiceRepository: InvoiceRepository
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var detailRepository: DetailRepository

    fun list ():List<Detail>{
        return detailRepository.findAll()
    }

    fun save(detail: Detail):Detail{
        try {
            // Verification logic for invoice and product existence
            detail.invoiceId?.let { invoiceId ->
                if (!invoiceRepository.existsById(invoiceId)) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found for id: $invoiceId")
                }
            }

            detail.productId?.let { productId ->
                if (!productRepository.existsById(productId)) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: $productId")
                }
            }

            // Save the detail
            return detailRepository.save(detail)
        } catch (ex: Exception) {
            // Handle exceptions by wrapping them in a ResponseStatusException
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing the request", ex)
        }
    }
    fun update(detail: Detail): Detail {
        try {
            detailRepository.findById(detail.id)
                ?: throw Exception("ID no existe")

            return detailRepository.save(detail)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun updateName(detail: Detail): Detail{
        try{
            val response = detailRepository.findById(detail.id)
                ?: throw Exception("ID no existe")
            response.apply {
                quantity=detail.quantity //un atributo del modelo
            }
            return detailRepository.save(response)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun delete (id: Long?):Boolean?{
        try{
            val response = detailRepository.findById(id)
                ?: throw Exception("ID no existe")
            detailRepository.deleteById(id!!)
            return true
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
    fun listById (id:Long?): Detail?{
        return detailRepository.findById(id)
    }

}