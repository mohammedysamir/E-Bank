package com.myasser.e_bank

class Transaction(private val fromCustomerName: String, private val toCustomerName: String, private val transactionAmount: Float) {
    fun getToCustomer(): String {
        return toCustomerName
    }

    fun getFromCustomer(): String {
        return fromCustomerName
    }

    fun getAmountTransferred(): Float {
        return transactionAmount
    }
}