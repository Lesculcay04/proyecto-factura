package com.proyectoleslie.factura.service

import com.google.gson.Gson
import com.proyectoleslie.factura.model.Client
import com.proyectoleslie.factura.model.Invoice
import com.proyectoleslie.factura.repository.ClientRepository
import com.proyectoleslie.factura.repository.InvoiceRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class InvoiceServiceTest {

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    @Mock
    lateinit var invoiceRepository: InvoiceRepository

    @Mock
    lateinit var clientRepository: ClientRepository

    val jsonStringInvoice = File("./src/test/resources/invoice.json").readText(Charsets.UTF_8)
    val invoiceMock = Gson().fromJson(jsonStringInvoice, Invoice::class.java)
    val jsonStringClient = File("./src/test/resources/client.json").readText(Charsets.UTF_8)
    val clientMock = Gson().fromJson(jsonStringClient, Client::class.java)

    @Test
    fun saveInvoiceWhenIsCorrect(){
        Mockito.`when`(clientRepository.findById(invoiceMock.clientId)).thenReturn(clientMock)
        Mockito.`when`(invoiceRepository.save(Mockito.any(Invoice::class.java))).thenReturn(invoiceMock)
        val actualResponse = invoiceService.save(invoiceMock)
        Assertions.assertEquals(actualResponse.id, invoiceMock.id)
        //Lee el archivo desde l ruta
        val jsonString = File("./src/test/resources/invoice.json").readText(Charsets.UTF_8)
        // convierte en objeto de tipo Invoice
        val invoiceMock = Gson().fromJson(jsonString, Invoice::class.java)
    }

}

