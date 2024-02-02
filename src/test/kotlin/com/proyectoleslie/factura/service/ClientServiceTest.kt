package com.proyectoleslie.factura.service

import com.google.gson.Gson
import com.proyectoleslie.factura.model.Client
import com.proyectoleslie.factura.repository.ClientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File


@SpringBootTest
class ClientServiceTest {

    @InjectMocks
    lateinit var clientService: ClientService //clae que se va a probar

    @Mock   //objeto simulado
    lateinit var clientRepository: ClientRepository

    val jsonStringClient = File("./src/test/resources/client.json").readText(Charsets.UTF_8)
    val clientMock = Gson().fromJson(jsonStringClient, Client::class.java)


    @Test
    fun saveClientCorrect(){
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.save(clientMock)
        Assertions.assertEquals(response.id, clientMock.id)
    }


    @Test
    fun saveClientWhenFullnameIsBlank(){

        Assertions.assertThrows(Exception::class.java) {
            clientMock.apply { fullname=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)

        }
    }
    @Test
    fun saveClientWhenAddressIsBlack(){
        Assertions.assertThrows(Exception::class.java) {
            clientMock.apply { address=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)

        }
    }
}