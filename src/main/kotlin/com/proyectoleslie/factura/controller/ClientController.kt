package com.proyectoleslie.factura.controller

import com.proyectoleslie.factura.model.Client
import com.proyectoleslie.factura.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/client")   //endpoint
@CrossOrigin(methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE])
class ClientController {
    @Autowired
    lateinit var clientService: ClientService

    @GetMapping
    fun list ():List <Client>{
        return clientService.list()
    }

    @PostMapping
    fun save (@RequestBody client: Client): ResponseEntity<Client> {
        return ResponseEntity(clientService.save(client), HttpStatus.OK )
    }

    @PutMapping
    fun update (@RequestBody client: Client): ResponseEntity<Client> {
        return ResponseEntity(clientService.update(client), HttpStatus.OK)
    }

    @PatchMapping
    fun updateName (@RequestBody client: Client): ResponseEntity<Client> {
        return ResponseEntity(clientService.updateName(client), HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun delete (@PathVariable("id") id: Long):Boolean? {
        return clientService.delete(id)
    }

    @GetMapping("/{id}")
    fun listById (@PathVariable("id") id: Long): ResponseEntity<*> {
        return ResponseEntity(clientService.listById (id), HttpStatus.OK)

    }
}