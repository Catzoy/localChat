package com.tripled.localChat.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripled.localChat.R
import com.tripled.localChat.logic.User

class ChatsRvAdapter : RecyclerView.Adapter<ChatsRvAdapter.ViewHolder>() {
    private val users = mutableListOf<User>(
        User("192.168.123.44", "Dima"),
        User("192.168.123.168", "Sasha"),
        User("192.168.123.90", "Illya"),
        User("192.168.123.183")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.inherit(users[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName = itemView.findViewById<TextView>(R.id.user_name)
        private val userIp = itemView.findViewById<TextView>(R.id.user_ip)

        fun inherit(user: User) {
            userName.text = user.name ?: "None"
            userIp.text = user.ip
        }
    }
}