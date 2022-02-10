package com.myasser.e_bank

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var customerName: String
    private lateinit var customerPhone: String
    private lateinit var customerEmail: String
    private var customerImage: Int = 0
    private var customerBalance: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        //initialize attributes
        customerBalance = intent.getFloatExtra("Customer Balance", 0.00f)
        customerEmail = intent.getStringExtra("Customer Email").toString()
        customerImage = intent.getIntExtra("Customer Image",0)
        customerName = intent.getStringExtra("Customer Name").toString()
        customerPhone = intent.getStringExtra("Customer Phone").toString()

        //represent customer's data
        findViewById<org.fabiomsr.moneytextview.MoneyTextView>(R.id.balance).amount = customerBalance
        findViewById<TextView>(R.id.email).text = customerEmail
        findViewById<TextView>(R.id.phone).text = customerPhone
        findViewById<ImageView>(R.id.profile_image).setImageResource(customerImage)
        findViewById<TextView>(R.id.user_name).text = customerName

        findViewById<Button>(R.id.transferMoneyButton).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.transferMoneyButton -> {
                //2 scenarios
                //1: current profile belongs to main customer then navigate to view all customers screen
                if (findViewById<TextView>(R.id.user_name).text == SplashActivity.currentCustomer.getCustomerName())
                    startActivity(Intent(this@ProfileActivity, ViewCustomerActivity::class.java))
                //TODO: 2- current profile in non-main customer then open transfer dialog, add transaction to the table and increase balance
                else {
                    val dbHelper = DBHelper(this)
                    //build dialog
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.transaction_dialog)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//set transparent background
                    val transactionTextView = dialog.findViewById(R.id.transactionInput) as TextInputEditText
                    val confirmButton = dialog.findViewById(R.id.confirm_button) as Button
                    confirmButton.setOnClickListener {
                        val transactionAmount: Float = transactionTextView.text.toString().toFloat()
                        val currentCustomer = SplashActivity.currentCustomer

                        if (transactionAmount <= currentCustomer.getCustomerBalance()) {
                            dbHelper.updateCustomerBalance(currentCustomer, -transactionAmount)
                            currentCustomer.updateBalance(-transactionAmount) //subtract transferred amount
                            //update selected customer's balance
                            val selectedCustomer = Customer(customerName, customerPhone, customerEmail, customerImage, customerBalance)
                            dbHelper.addTransaction(currentCustomer, selectedCustomer, transactionAmount) //add transaction
                            dbHelper.updateCustomerBalance(selectedCustomer, transactionAmount)//update database
                        } else
                            Toast.makeText(this, "Current balance is less than $transactionAmount", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }
    }
}