package com.kamranyaseen.webflux.controller

import javax.annotation.PostConstruct

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
 
import com.javasampleapproach.webflux.model.Customer
import com.javasampleapproach.webflux.model.Address
 
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/api/customer")
class RestControllerAPIs {
	
	val log = LoggerFactory.getLogger(RestControllerAPIs::class.java);
	
	// Define customer storage
	val custStores = mutableMapOf<Long, Customer>()
	
	@PostConstruct
    fun initial(){
        custStores.put(1, Customer(1, "Jack", "Smith", 20, Address("NANTERRE CT", "77471")))
        custStores.put(2, Customer(2, "Peter", "Johnson", 25, Address("W NORMA ST", "77009")))
    }
	
	@GetMapping
    fun getAll(): Flux<Customer> {
    	return Flux.fromIterable(ArrayList(custStores.values));
    }
	
	@GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Long): Mono<Customer> {
		return Mono.justOrEmpty(custStores.get(id))
    }
	
	@PostMapping("/post")
    fun postCustomer(@RequestBody customer: Customer): Mono<ResponseEntity<String>>{
		// do post
		custStores.put(customer.id, customer)
		
		log.info("########### POST:" + customer)
		
        return Mono.just(ResponseEntity("Post Successfully!", HttpStatus.CREATED))
    }
	
    @PutMapping("/put/{id}")
    fun putCustomer(@PathVariable id: Long, @RequestBody customer: Customer): Mono<ResponseEntity<Customer>>{
		// reset customer.Id
		customer.id = id;
		
		if(custStores.get(id) != null){
			custStores.replace(id, customer)
		}else{
			customer.id = id
			custStores.put(id, customer)
		}
		
		log.info("########### PUT:" + customer)
		return Mono.just(ResponseEntity(customer, HttpStatus.CREATED))
    }
	
	@DeleteMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Long): Mono<ResponseEntity<String>> {
		val cust = custStores.remove(id)
		if(cust != null){
			log.info("########### DELETE:" + cust)
		}else{
			log.info("########### Don't exist any customer with id = ${id}")
		}
		
		return Mono.just(ResponseEntity("Delete Succesfully!", HttpStatus.ACCEPTED))
    }
}