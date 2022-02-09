package com.myasser.e_bank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewCustomerActitiy : AppCompatActivity() {

    private lateinit var linearManager: LinearLayoutManager
    private lateinit var databaseHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_customer)

        linearManager = LinearLayoutManager(this)
        val customerView = findViewById<RecyclerView>(R.id.customerView) //get recycler view from XML
        customerView.layoutManager = linearManager
        databaseHelper = DBHelper(applicationContext)
        //if the customer table is not empty then show
        if (!databaseHelper.isTableEmpty(databaseHelper.customerTable)) { //if customer table is not empty then show
            val customerList =
                databaseHelper.readCustomers() //get list of customers
            val adapter = CustomerRecyclerView(customerList,applicationContext)
            customerView.adapter = adapter //assigned to the recycler view to show
        }
    }
}