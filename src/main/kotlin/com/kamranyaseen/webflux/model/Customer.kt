package com.kamranyaseen.webflux.model

data class Customer(
		var id: Long = -1,
		var firstName: String? = null,
		var lastName: String? = null,
		var age: Int? = null,
		var address: Address = Address()) {
}