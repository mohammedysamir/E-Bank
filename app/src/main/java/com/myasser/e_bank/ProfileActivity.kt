package com.myasser.e_bank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myasser.e_bank.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var profileBinding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
    }

    fun initProfile(customer: Customer) {
        profileBinding.balance.amount = customer.getCustomerBalance()
        profileBinding.email.text = customer.getCustomerEmail()
        profileBinding.phone.text = customer.getCustomerPhone()
        profileBinding.profileImage.setImageResource(customer.getCustomerImage())
        profileBinding.userName.text = customer.getCustomerName()
    }
}