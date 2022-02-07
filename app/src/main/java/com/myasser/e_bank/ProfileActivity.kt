package com.myasser.e_bank

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        findViewById<org.fabiomsr.moneytextview.MoneyTextView>(R.id.balance).amount = intent.getFloatExtra("Customer Balance",0.00f)
        findViewById<TextView>(R.id.email).text = intent.getStringExtra("Customer Email")
        findViewById<TextView>(R.id.phone).text = intent.getStringExtra("Customer Phone")
        findViewById<ImageView>(R.id.profile_image).setImageResource(intent.getIntExtra("Customer Image",0))
        findViewById<TextView>(R.id.user_name).text = intent.getStringExtra("Customer Name")
    }
}

//TODO: handle button if this profile main's then navigate to view all customers, else open transfer dialog