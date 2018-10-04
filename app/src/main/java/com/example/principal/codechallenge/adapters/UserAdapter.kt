package com.example.principal.codechallenge.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.UserDatabase
import kotlinx.android.synthetic.main.user_item_list.view.*

class UserAdapter (val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    var items: List<UserDatabase> = listOf()


    override fun getItemCount(): Int {
        return items.size
    }

    fun setDataset(users: List<UserDatabase>?){

        items = users!!
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.user_name?.text = items.get(position).username
        holder.rank?.text = items.get(position).rank.toString()
        holder.best_lang?.text = String.format(context.resources.getString(R.string.best_language), items.get(position).language, items.get(position).points)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val user_name = view.user_name
    val rank = view.rank
    val best_lang = view.best_language
}
