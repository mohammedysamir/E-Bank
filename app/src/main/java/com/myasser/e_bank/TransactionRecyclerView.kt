package com.myasser.e_bank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TransactionRecyclerView(val list: ArrayList<Transaction>) : RecyclerView.Adapter<TransactionRecyclerView.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toCustomer = itemView.findViewById<TextView>(R.id.to_customer_name)
        val transactionAmount = itemView.findViewById<org.fabiomsr.moneytextview.MoneyTextView>(R.id.transaction_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.transaction_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionItem = list[position]
        holder.toCustomer.text = transactionItem.getToCustomer()
        holder.transactionAmount.amount = transactionItem.getAmountTransferred()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}