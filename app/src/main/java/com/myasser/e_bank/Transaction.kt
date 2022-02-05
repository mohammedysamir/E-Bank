package com.myasser.e_bank

class Transaction(private val toCustomerName: String, private val transactionAmount: Float) {
    fun getToCustomer(): String {
        return toCustomerName
    }

    fun getAmountTransferred(): Float {
        return transactionAmount
    }
}