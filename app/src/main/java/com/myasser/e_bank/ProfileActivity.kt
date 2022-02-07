package com.myasser.e_bank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        findViewById<org.fabiomsr.moneytextview.MoneyTextView>(R.id.balance).amount = intent.getFloatExtra("Customer Balance",0.00f)
        findViewById<TextView>(R.id.email).text = intent.getStringExtra("Customer Email")
        findViewById<TextView>(R.id.phone).text = intent.getStringExtra("Customer Phone")
        findViewById<ImageView>(R.id.profile_image).setImageResource(intent.getIntExtra("Customer Image",0))
        findViewById<TextView>(R.id.user_name).text = intent.getStringExtra("Customer Name")

        findViewById<Button>(R.id.transferMoneyButton).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.transferMoneyButton->{
                //2 scenarios
                //1: current profile belongs to main customer then navigate to view all customers screen
                if(findViewById<TextView>(R.id.user_name).text==SplashActivity.mainCustomer.getCustomerName())
                    startActivity(Intent(this@ProfileActivity,ViewCustomerActitiy::class.java))
                //TODO: 2- current profile in non-main customer then open transfer dialog, add transaction to the table and increase balance

            }
        }
    }
}

//TODO: handle button if this profile main's then navigate to view all customers, else open transfer dialog