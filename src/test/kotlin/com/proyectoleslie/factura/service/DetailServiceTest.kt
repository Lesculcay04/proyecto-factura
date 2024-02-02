package com.proyectoleslie.factura.service

import com.google.gson.Gson
import com.proyectoleslie.factura.model.Detail
import com.proyectoleslie.factura.model.Invoice
import com.proyectoleslie.factura.model.Product
import com.proyectoleslie.factura.repository.DetailRepository
import com.proyectoleslie.factura.repository.InvoiceRepository
import com.proyectoleslie.factura.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class DetailServiceTest {
    @InjectMocks
    lateinit var detailService: DetailService

    @Mock
    lateinit var invoiceRepository: InvoiceRepository

    @Mock
    lateinit var productRepository: ProductRepository

    @Mock
    lateinit var detailRepository: DetailRepository

    val jsonStringDetail = File("./src/test/resources/detail.json").readText(Charsets.UTF_8)
    val detailMock = Gson().fromJson(jsonStringDetail, Detail::class.java)
    val jsonStringInvoice = File("./src/test/resources/invoice.json").readText(Charsets.UTF_8)
    val invoiceMock = Gson().fromJson(jsonStringInvoice, Invoice::class.java)
    val jsonStringProduct = File("./src/test/resources/product.json").readText(Charsets.UTF_8)
    val productMock = Gson().fromJson(jsonStringProduct, Product::class.java)


    @Test
    fun saveClientCorrect() {
        fun saveInvoiceWhenIsCorrect(){
            // Configuración de simulaciones para findById
            Mockito.`when`(invoiceRepository.findById(detailMock.invoiceId)).thenReturn(invoiceMock)
            Mockito.`when`(productRepository.findById(detailMock.productId)).thenReturn(productMock)

            // Configuración de simulación para save en detailRepository
            Mockito.`when`(detailRepository.save(Mockito.any(Detail::class.java))).thenReturn(detailMock)

            // Configuración de simulación para findByInvoiceId
            val listDetail = listOf(detailMock) // Puedes ajustar esto según tu lógica real

            Mockito.`when`(detailRepository.findByInvoiceId(detailMock.invoiceId)).thenReturn(listDetail)

            // Configuración de simulación para findById en invoiceRepository
            Mockito.`when`(invoiceRepository.findById(detailMock.invoiceId)).thenReturn(invoiceMock)

            // Llamada al método que se está probando
            val response = detailService.save(detailMock)

            // Verificación de resultados esperados
            Assertions.assertEquals(response.id, detailMock.id)
        }
    }
}