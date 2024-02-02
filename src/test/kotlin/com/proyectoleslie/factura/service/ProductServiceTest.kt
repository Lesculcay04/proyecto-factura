package com.proyectoleslie.factura.service


import com.google.gson.Gson
import com.proyectoleslie.factura.model.Product
import com.proyectoleslie.factura.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class ProductServiceTest {
    @InjectMocks
    lateinit var productService: ProductService//clae que se va a probar

    @Mock   //objeto simulado
    lateinit var productRepository: ProductRepository

    val jsonString = File("./src/test/resources/product.json").readText(Charsets.UTF_8)
    val productMock = Gson().fromJson(jsonString, Product::class.java)

    @Test
    fun saveProductCorrect(){
        Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(productMock)
        val response = productService.save(productMock)
        Assertions.assertEquals(response.id, productMock.id)
    }


    @Test
    fun saveProductWhenFullnameIsBlank(){

        Assertions.assertThrows(Exception::class.java) {
            productMock.apply { description=" "}
            Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(productMock)
            productService.save(productMock)

        }
    }
    @Test
    fun saveClientWhenAddressIsBlack(){
        Assertions.assertThrows(Exception::class.java) {
            productMock.apply { stok= -1}
            Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(productMock)
            productService.save(productMock)

        }
    }
}