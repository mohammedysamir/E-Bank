package com.myasser.e_bank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomerRecyclerView(val list: ArrayList<Customer>) : RecyclerView.Adapter<CustomerRecyclerView.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName = itemView.findViewById<TextView>(R.id.customer_name)
        val profile = itemView.findViewById<ImageView>(R.id.profile_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.customer_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customerItem = list[position]
        holder.customerName.text = customerItem.getCustomerName()
        holder.profile.setOnClickListener {
            //Navigate to profile and set data
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}