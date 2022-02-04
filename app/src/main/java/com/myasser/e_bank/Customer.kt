package com.myasser.e_bank

//this is a data class for a customer
class Customer(private var name: String, private var phone: String, private var email: String, private val image_path:String, private var balance: Float) {
    fun updateBalance(new_balance: Float) {
        balance = new_balance
    }

    fun getCustomerName(): String {
        return name
    }

    fun getCustomerPhone(): String {
        return phone
    }

    fun getCustomerEmail(): String {
        return email
    }
}