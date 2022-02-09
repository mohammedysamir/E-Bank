package com.myasser.e_bank

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.util.*

class SplashActivity : AppCompatActivity() {
    companion object {
        lateinit var currentCustomer: Customer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this).load(R.drawable.dual_ball_loading).into(findViewById(R.id.loader_image))
        initiateData()
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
        }, 3500)
    }

    private fun initiateData() {
        val db = DBHelper(applicationContext)
        if (db.isTableEmpty(db.customerTable)) //Check if the customer table is empty or not -> if empty then initiate customers
        {
            val customerList = ArrayList<Customer>()
            customerList.add(Customer("Mohammed Yasser", "01129550094", "xyz@gmail.com", R.drawable.p3, 2000f))
            customerList.add(Customer("Mostafa", "00012143124", "xxz@gmail.com", R.drawable.p7, 5300.23f))
            customerList.add(Customer("Nabil", "01241251251", "122z@gmail.com", R.drawable.p4, 628.2f))
            customerList.add(Customer("Mohammed H", "03468710211", "46fdgz@gmail.com", R.drawable.p8, 2352.7f))
            customerList.add(Customer("Mariem H", "01241264581", "36463df@gmail.com", R.drawable.p2, 10000.2352f))
            customerList.add(Customer("Ahmed Y", "05732341238", "xvxc@gmail.com", R.drawable.p1, 12915.64f))
            customerList.add(Customer("Kitty", "01251526346", "wfdhx@gmail.com", R.drawable.p9, 86455.95f))
            customerList.add(Customer("Loay", "07834923411", "wxvsdwr@gmail.com", R.drawable.p5, 75459.6f))
            customerList.add(Customer("Ahmed A", "08691226124", "24scxc@gmail.com", R.drawable.p10, 99.99f))
            customerList.add(Customer("Mohammed L", "01267346713", "gdhyw1@gmail.com", R.drawable.p6, 636.15f))
            db.addCustomerList(customerList)
        }
        //get main customer -> customer #0 in the db
        currentCustomer = db.readCustomers()[0]
    }
}