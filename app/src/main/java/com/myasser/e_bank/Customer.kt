package com.myasser.e_bank

//this is a data class for a customer
class Customer(
    private var name: String,
    private var phone: String,
    private var email: String,
    private val image_path: Int,
    private var balance: Float
) {
    fun updateBalance(new_balance: Float) {
        balance = new_balance
    }

    fun getCustomerName(): String {
        return name
    }

    fun getCustomerImage(): Int {
        return image_path
    }

    fun getCustomerBalance(): Float {
        return balance
    }

    fun getCustomerPhone(): String {
        return phone
    }

    fun getCustomerEmail(): String {
        return email
    }
}