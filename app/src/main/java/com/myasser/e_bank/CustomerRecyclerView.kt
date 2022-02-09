package com.myasser.e_bank

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomerRecyclerView(val list: ArrayList<Customer>, val context: Context) :
    RecyclerView.Adapter<CustomerRecyclerView.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customer_name)
        val profile: ImageView = itemView.findViewById(R.id.profile_icon)
        val customerCard: ConstraintLayout = itemView.findViewById(R.id.customerInfoCard)
        var isSelected: Boolean = false;
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
            if (holder.isSelected) {
                holder.customerCard.setBackgroundResource(R.drawable.unselected_customer)
                holder.isSelected = false
            } else //not selected
            {
                holder.customerCard.setBackgroundResource(R.drawable.selected_customer)
                holder.isSelected = true
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}