package com.myasser.e_bank

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

const val databaseName = "Customers"
const val version = 1
const val name_column = "Name"
const val email_column = "Email"
const val phone_column = "Phone"
const val balance_column = "Balance"
const val image_column = "ImagePath"
const val amount_column = "Amount"

class DBHelper(private val context: Context) :
    SQLiteOpenHelper(context, databaseName, null, version) {
    private val customerTable = "CustomerTable"
    private val transactionTable = "TransactionTable"
    private val customerTableQuery =
        "Create Table $customerTable ($name_column Varchar(20) Not Null primary key, $email_column Varchar(20) Not Null primary key, $phone_column INTEGER Not Null, $balance_column Real, $image_column TEXT );"
    private val transactionTableQuery =
        "Create Table $transactionTable ($name_column Varchar(20) Not Null primary key,$amount_column Real);"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(customerTableQuery)
        db?.execSQL(transactionTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {}

    fun addCustomer(customer: Customer) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Name", customer.getCustomerName())
        contentValues.put("Email", customer.getCustomerEmail())
        contentValues.put("Phone", customer.getCustomerPhone())
        contentValues.put("Balance", customer.getCustomerBalance())
        contentValues.put("Image", customer.getCustomerImage())

        database.insert(customerTable, null, contentValues)
    }

    fun addTransaction(toCustomer: Customer, amount: Float) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Name", toCustomer.getCustomerName())
        contentValues.put("Amount", amount)

        database.insert(transactionTable, null, contentValues)
    }

    @SuppressLint("Range")
    fun readCustomers(): ArrayList<Customer> {
        val customerList = ArrayList<Customer>()
        val reader = this.readableDatabase
        val result = reader.rawQuery("Select * from $customerTable", null)
        if (result.moveToFirst()) {
            do {
                val name: String = result.getString(result.getColumnIndex(name_column))
                val email: String = result.getString(result.getColumnIndex(email_column))
                val phone: String = result.getString(result.getColumnIndex(phone_column))
                val balance: Float = result.getFloat(result.getColumnIndex(balance_column))
                val image: Int = result.getInt(result.getColumnIndex(image_column))
                customerList.add(Customer(name, phone, email, image, balance))
            } while (result.moveToNext())
        }
        result.close()
        return customerList
    }

    @SuppressLint("Range")
    fun readTransactions(): ArrayList<Transaction> {
        val transactionList = ArrayList<Transaction>()
        val reader = this.readableDatabase
        val result = reader.rawQuery("Select * from $this.transactionTable", null)
        if (result.moveToFirst()) {
            do {
                val name: String = result.getString(result.getColumnIndex(name_column))
                val amount: Float = result.getFloat(result.getColumnIndex(amount_column))
                transactionList.add(Transaction(name, amount))
            } while (result.moveToNext())
        }
        result.close()
        return transactionList
    }
}