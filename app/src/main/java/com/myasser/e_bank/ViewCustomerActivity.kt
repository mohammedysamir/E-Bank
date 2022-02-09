package com.myasser.e_bank

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class ViewCustomerActivity : AppCompatActivity(), View.OnClickListener {

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

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.transfer_button->{
                //TODO: whenever the user clicks on the transfer selected button, open dialog that request the amount to be transferred and get all customers selected and add this to their balance
                //build dialog
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.transaction_dialog)
                val transactionTextView = dialog.findViewById(R.id.transactionInput) as TextInputEditText
                val confirmButton = dialog.findViewById(R.id.confirm_button) as Button
                confirmButton.setOnClickListener {
                    val transactionAmount:Float=transactionTextView.text.toString().toFloat()
                    SplashActivity.currentCustomer.updateBalance(-transactionAmount) //reduce balance
                    //get list of selected customers
                }
                dialog.show()


            }
        }
    }
}