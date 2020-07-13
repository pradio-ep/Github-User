package com.pradioep.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pradioep.githubuser.R
import com.pradioep.githubuser.helper.UtilityHelper
import com.pradioep.githubuser.model.Item

class UserAdapter(private val context: Context, private val list: ArrayList<Item>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: ArrayList<Item>) {
        list.clear()
        list.addAll(newList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        UtilityHelper.setImage(context, user.avatar_url, holder.imgUser)
        holder.tvName.text = user.login
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgUser: ImageView = itemView.findViewById(R.id.img_user)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}