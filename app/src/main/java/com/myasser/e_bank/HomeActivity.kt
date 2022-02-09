package com.myasser.e_bank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.fabiomsr.moneytextview.MoneyTextView

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var linearManager: LinearLayoutManager
    private lateinit var databaseHelper: DBHelper
    private lateinit var mainCustomer: Customer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<ImageView>(R.id.profile_icon).setOnClickListener(this)
        findViewById<Button>(R.id.view_customer_button).setOnClickListener(this)
        //get main customer from the db
        mainCustomer = SplashActivity.currentCustomer
        //initiate the transaction recycler view
        linearManager = LinearLayoutManager(this)
//        val transactionView = findViewById<RecyclerView>(R.id.transaction_view)
//        transactionView.layoutManager = linearManager
//        databaseHelper = DBHelper(applicationContext)
//        //if the transaction table is not empty then show
//        if (!databaseHelper.isTableEmpty(databaseHelper.transactionTable)) {
//            val transactionsList =
//                databaseHelper.readTransactionsForCustomer(mainCustomer.getCustomerName())
//            val adapter = TransactionRecyclerView(transactionsList)
//            transactionView.adapter = adapter
//        }
//        //initialize home screen with customer info
//        findViewById<TextView>(R.id.welcome_statement).text="Welcome,"+mainCustomer.getCustomerName()
//        findViewById<MoneyTextView>(R.id.balance).amount=mainCustomer.getCustomerBalance()
        fetchCustomerData()
    }

    override fun onStart() {
        super.onStart()
        fetchCustomerData()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.profile_icon -> {
                //navigate to profile screen and pass main customer intent
                val profileInent = Intent(this@HomeActivity, ProfileActivity::class.java)
                profileInent.putExtra("Customer Name", mainCustomer.getCustomerName())
                profileInent.putExtra("Customer Phone", mainCustomer.getCustomerPhone())
                profileInent.putExtra("Customer Email", mainCustomer.getCustomerEmail())
                profileInent.putExtra("Customer Image", mainCustomer.getCustomerImage())
                profileInent.putExtra("Customer Balance", mainCustomer.getCustomerBalance())
                startActivity(profileInent)
            }
            R.id.view_customer_button -> {
                //navigate to all_customer screen
                startActivity(Intent(this@HomeActivity,ViewCustomerActivity::class.java))
            }
        }
    }

    fun fetchCustomerData(){
        //fetch and update customer data
        val transactionView = findViewById<RecyclerView>(R.id.transaction_view)
        transactionView.layoutManager = linearManager
        databaseHelper = DBHelper(applicationContext)
        //if the transaction table is not empty then show
        if (!databaseHelper.isTableEmpty(databaseHelper.transactionTable)) {
            val transactionsList =
                    databaseHelper.readTransactionsForCustomer(mainCustomer.getCustomerName())
            val adapter = TransactionRecyclerView(transactionsList)
            transactionView.adapter = adapter
        }
        //initialize home screen with customer info
        findViewById<TextView>(R.id.welcome_statement).text="Welcome,"+mainCustomer.getCustomerName()
        findViewById<MoneyTextView>(R.id.balance).amount=mainCustomer.getCustomerBalance()
    }
}