package com.myasser.e_bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.myasser.e_bank.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).load(R.drawable.dual_ball_loading).into(binding.loaderImage)

        val handler=Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashActivity,HomeActivity::class.java)
            startActivity(intent)
        },2500)
    }
}