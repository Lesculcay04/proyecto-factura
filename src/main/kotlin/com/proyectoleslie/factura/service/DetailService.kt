package com.proyectoleslie.factura.service

import com.proyectoleslie.factura.model.Detail
import com.proyectoleslie.factura.repository.DetailRepository
import com.proyectoleslie.factura.repository.InvoiceRepository
import com.proyectoleslie.factura.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.collections.*
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

    fun save(detail: Detail): Detail {
        try {
            // Verification logic for invoice and product existence
            detail.invoiceId?.let { invoiceId ->
                // Check if invoice exists
                if (!invoiceRepository.existsById(invoiceId)) {
                    // If invoice does not exist, you may choose to handle it gracefully
                    // For now, I'm just logging a message, but you can adjust as needed
                    println("Invoice not found for id: $invoiceId")
                    // You can return an appropriate response or handle it differently based on your requirements
                    // For example, return an error response or throw a custom exception
                }
            }

            detail.productId?.let { productId ->
                // Check if product exists
                if (!productRepository.existsById(productId)) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: $productId")
                }
            }//codigo hecho por mi.
            val response = detailRepository.save(detail)
            //logica disminuir detail
            val product = productRepository.findById(detail.productId)
            product?.apply{
                stok = stok?.minus(detail.quantity!!)
            }
            productRepository.save(product!!)

            //productos que se multipliquen y muestre en total
            val listDetail = detailRepository.findByInvoiceId(detail.invoiceId)

                if (listDetail != null) {
                    var suma = 0.0

                    listDetail.forEach { element ->
                        suma += ((element.price ?: 0L).toDouble() * (element.quantity ?: 0L)).toDouble()
                        // Multiplico y agrego a la suma
                }
                val invoiceToUp = invoiceRepository.findById(detail.invoiceId)
                invoiceToUp?.apply {
                    total = suma.toDouble()
                }
                invoiceRepository.save(invoiceToUp!!)
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

            val response = detailRepository.save(detail)
            //logica disminuir detail
            val product = productRepository.findById(detail.productId)
            product?.apply{
                stok = stok?.plus(detail.quantity!!)
            }
            productRepository.save(product!!)
            return response

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
            val detail = detailRepository.findById(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
            val product = productRepository.findById(detail.productId)
            product?.apply {
                stok = stok?.plus(detail.quantity!!)
            }
            productRepository.save(product!!)
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