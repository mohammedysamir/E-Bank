package com.myasser.e_bank

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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
        findViewById<Button>(R.id.transfer_button).setOnClickListener(this)

        linearManager = LinearLayoutManager(this)
        val customerView = findViewById<RecyclerView>(R.id.customerView) //get recycler view from XML
        customerView.layoutManager = linearManager
        databaseHelper = DBHelper(applicationContext)
        //if the customer table is not empty then show
        if (!databaseHelper.isTableEmpty(databaseHelper.customerTable)) { //if customer table is not empty then show
            val customerList =
                    databaseHelper.readCustomers() //get list of customers
            //TODO: remove currentCustomer from the list -potential bug-
            //remove current user from the list -> simple solution
            customerList.removeAt(0)
            val adapter = CustomerRecyclerView(customerList, applicationContext)
            customerView.adapter = adapter //assigned to the recycler view to show
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.transfer_button -> {
                val dbHelper = DBHelper(this)
                val selectedCustomers = CustomerRecyclerView.selectedCustomerList
                val currentCustomer = SplashActivity.currentCustomer
                if (selectedCustomers.size > 0) { //build dialog
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.transaction_dialog)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//set transparent background
                    val transactionTextView = dialog.findViewById(R.id.transactionInput) as TextInputEditText
                    val confirmButton = dialog.findViewById(R.id.confirm_button) as Button
                    confirmButton.setOnClickListener {
                        val transactionAmount: Float = transactionTextView.text.toString().toFloat()


                        if (transactionAmount * selectedCustomers.size <= currentCustomer.getCustomerBalance()) {
                            dbHelper.updateCustomerBalance(currentCustomer, -transactionAmount * selectedCustomers.size)
                            currentCustomer.updateBalance(-transactionAmount * selectedCustomers.size) //subtract balance*selected customers
                            //get list of selected customers
                            for (selectedCustomer in CustomerRecyclerView.selectedCustomerList) {
                                selectedCustomer.updateBalance(transactionAmount)
                                dbHelper.addTransaction(currentCustomer, selectedCustomer, transactionAmount) //add transaction
                                dbHelper.updateCustomerBalance(selectedCustomer, transactionAmount)//update database
                            }
                            Toast.makeText(this, "Transaction done ✔", Toast.LENGTH_LONG).show()
                        } else
                            Toast.makeText(this, "Current balance is less than ${transactionAmount * selectedCustomers.size}", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                } else
                    Toast.makeText(this, "You didn't select any customer", Toast.LENGTH_LONG).show()
            }
        }
    }
}