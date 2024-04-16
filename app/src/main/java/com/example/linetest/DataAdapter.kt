package com.example.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.linetest.DatabaseModel
import com.example.linetest.R


class DataAdapter(var list: ArrayList<DatabaseModel>):RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.tv_n)
        var email = itemView.findViewById<TextView>(R.id.tv_e)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_layout,null,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=list[position].name
        holder.email.text=list[position].email
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
