package com.myasser.e_bank

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomerRecyclerView(val list: ArrayList<Customer>, val context: Context) :
    RecyclerView.Adapter<CustomerRecyclerView.ViewHolder>() {
    //    val colorList: ColorStateList =ColorStateList(
//        arrayOf(
//            intArrayOf(android.R.attr.state_enabled), //enabled
//            intArrayOf(-android.R.attr.state_enabled), //disabled
//            intArrayOf(-android.R.attr.state_checked), // unchecked
//            intArrayOf(android.R.attr.state_pressed), //pressed
//            intArrayOf(-android.R.attr.state_pressed)// unpressed
//        ),
//        intArrayOf(
//            Color.TRANSPARENT,
//            R.color.indicator,
//        )
//    )
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName = itemView.findViewById<TextView>(R.id.customer_name)
        val profile = itemView.findViewById<ImageView>(R.id.profile_icon)
        val customerCard = itemView.findViewById<ConstraintLayout>(R.id.customerInfoCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.customer_row,
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customerItem = list[position]
        holder.customerName.text = customerItem.getCustomerName()
        holder.profile.setOnClickListener {
            //navigate to profile screen and pass customer info
            val profileIntent =
                Intent(context, ProfileActivity::class.java)
            profileIntent.putExtra("Customer Name", customerItem.getCustomerName())
            profileIntent.putExtra("Customer Phone", customerItem.getCustomerPhone())
            profileIntent.putExtra("Customer Email", customerItem.getCustomerEmail())
            profileIntent.putExtra("Customer Image", customerItem.getCustomerImage())
            profileIntent.putExtra("Customer Balance", customerItem.getCustomerBalance())
            profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(profileIntent)
        }

        holder.customerCard.setOnClickListener {
            //TODO: remove selection when click again, and added final selection to a list to transfer money to
            if (holder.customerCard.background == AppCompatResources.getDrawable(context,R.drawable.selected_customer)) //already selected
                holder.customerCard.background=AppCompatResources.getDrawable(context,R.drawable.unselected_customer)
            else //not selected
                holder.customerCard.background=AppCompatResources.getDrawable(context,(R.drawable.selected_customer))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}