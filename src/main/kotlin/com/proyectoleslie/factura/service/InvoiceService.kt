package com.proyectoleslie.factura.service

import com.proyectoleslie.factura.model.Invoice
import com.proyectoleslie.factura.repository.ClientRepository
import com.proyectoleslie.factura.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InvoiceService {
    @Autowired
    lateinit var clientRepository: ClientRepository
    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    fun list ():List<Invoice>{
        return invoiceRepository.findAll()
    }

    fun save(invoice: Invoice):Invoice{
        try {
            clientRepository.findById(invoice.clientId)
                ?: throw Exception("Id del cliente no encontrados")
            return invoiceRepository.save(invoice)
        }catch (ex : Exception){
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, ex.message, ex)
        }
    }
    fun update(invoice: Invoice): Invoice {
        try {
            invoiceRepository.findById(invoice.id)
                ?: throw Exception("ID no existe")

            return invoiceRepository.save(invoice)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun updateName(invoice: Invoice): Invoice{
        try{
            val response = invoiceRepository.findById(invoice.id)
                ?: throw Exception("ID no existe")
            response.apply {
                code=invoice.code //un atributo del modelo
            }
            return invoiceRepository.save(response)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun delete (id: Long?):Boolean?{
        try{
            val response = invoiceRepository.findById(id)
                ?: throw Exception("ID no existe")
            invoiceRepository.deleteById(id!!)
            return true
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
    fun listById (id:Long?): Invoice?{
        return invoiceRepository.findById(id)
    }

}