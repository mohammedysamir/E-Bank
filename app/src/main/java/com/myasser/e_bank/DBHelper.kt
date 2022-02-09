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
const val receiver_column = "To"
const val email_column = "Email"
const val phone_column = "Phone"
const val balance_column = "Balance"
const val image_column = "ImagePath"
const val amount_column = "Amount"

class DBHelper(context: Context) :
        SQLiteOpenHelper(context, databaseName, null, version) {
    val customerTable = "CustomerTable"
    val transactionTable = "TransactionTable"
    private val customerTableQuery =
            "Create Table $customerTable ($name_column Varchar(20) Not Null primary key, $email_column Varchar(20) Not Null unique, $phone_column INTEGER Not Null, $balance_column Real, $image_column TEXT );"
    private val transactionTableQuery =
            "Create Table $transactionTable ($name_column Varchar(20) Not Null,$receiver_column Varchar(20) Not Null ,$amount_column Real);"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(customerTableQuery)
        db?.execSQL(transactionTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {}

    fun addCustomer(customer: Customer) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(name_column, customer.getCustomerName())
        contentValues.put(email_column, customer.getCustomerEmail())
        contentValues.put(phone_column, customer.getCustomerPhone())
        contentValues.put(balance_column, customer.getCustomerBalance())
        contentValues.put(image_column, customer.getCustomerImage())

        database.insert(customerTable, null, contentValues)
    }

    fun addCustomerList(customerList: ArrayList<Customer>) {
        val database = this.writableDatabase
        for (customer in customerList) {
            val contentValues = ContentValues()
            contentValues.put(name_column, customer.getCustomerName())
            contentValues.put(email_column, customer.getCustomerEmail())
            contentValues.put(phone_column, customer.getCustomerPhone())
            contentValues.put(balance_column, customer.getCustomerBalance())
            contentValues.put(image_column, customer.getCustomerImage())
            database.insert(customerTable, null, contentValues)
        }
    }

    fun addTransaction(fromCustomer:Customer,toCustomer: Customer, amount: Float) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(name_column, fromCustomer.getCustomerName())
        contentValues.put(receiver_column, toCustomer.getCustomerName())
        contentValues.put(amount_column, amount)

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

    fun updateCustomerBalance(db: SQLiteDatabase?, customer: Customer, amount: Float) {
        val strSQL =
                "UPDATE myTable SET $balance_column = ${customer.getCustomerBalance() + amount} WHERE $name_column = ${customer.getCustomerName()}"
        db?.execSQL(strSQL)
    }

    @SuppressLint("Range")
    fun readTransactions(): ArrayList<Transaction> {
        val transactionList = ArrayList<Transaction>()
        val reader = this.readableDatabase
        val result = reader.rawQuery("Select * from $this.transactionTable", null)
        if (result.moveToFirst()) {
            do {
                val fromCustomerName: String = result.getString(result.getColumnIndex(name_column))
                val toCustomerName: String = result.getString(result.getColumnIndex(receiver_column))
                val amount: Float = result.getFloat(result.getColumnIndex(amount_column))
                transactionList.add(Transaction(fromCustomerName, toCustomerName, amount))
            } while (result.moveToNext())
        }
        result.close()
        return transactionList
    }

    @SuppressLint("Range")
    fun readTransactionsForCustomer(customer_name: String): ArrayList<Transaction> {
        val transactionList = ArrayList<Transaction>()
        val reader = this.readableDatabase
        val result = reader.rawQuery(
                "Select $receiver_column,$amount_column from $transactionTable where $name_column = [$customer_name];",
                null
        )
        if (result.moveToFirst()) {
            do {
                val toCustomerName: String = result.getString(result.getColumnIndex(receiver_column))
                val amount: Float = result.getFloat(result.getColumnIndex(amount_column))
                transactionList.add(Transaction(customer_name, toCustomerName, amount))
            } while (result.moveToNext())
        }
        result.close()
        return transactionList
    }

    @SuppressLint("Recycle")
    fun isTableEmpty(tableName: String): Boolean {
        val db = this.writableDatabase
        val query = "SELECT count(*) FROM $tableName";
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst();
        val count: Int = cursor.getInt(0);
        if (count > 0)
            return false
        return true
    }
}