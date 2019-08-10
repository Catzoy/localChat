package com.tripled.localChat.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripled.localChat.R
import com.tripled.localChat.logic.User
import com.tripled.localChat.ui.helpingViews.StatusCircle

class ChatsRvAdapter : RecyclerView.Adapter<ChatsRvAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName = itemView.findViewById<TextView>(R.id.user_name)
        private val userIp = itemView.findViewById<TextView>(R.id.user_ip)
        private val userStatus = itemView.findViewById<StatusCircle>(R.id.status_circle)

        fun inherit(user: User) {
            val context = userName.context
            userName.text = user.name ?: context.getString(R.string.default_user_name)
            userIp.text = context.getString(R.string.ip, user.ip)
            userStatus.setColor(user.colorForState)
        }
    }

    private val users = mutableListOf<User>(
        User("aa", User.UserState.Connected, "192.168.123.44", "Dima"),
        User("bb", User.UserState.Unavailable, "192.168.123.168", "Sasha"),
        User("cc", User.UserState.HasMessages, "192.168.123.90", "Illya"),
        User("dd", "192.168.123.183")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.inherit(users[position])
    }

    fun userFound(user: User) {
        val indexOfUser = users.indexOfFirst { it.id == user.id }
        if (indexOfUser != -1) {
            users[indexOfUser] = user
            notifyItemChanged(indexOfUser)
        } else {
            users.add(user)
            notifyItemInserted(users.size - 1)
        }
    }

    fun userDisconnected(ip: String) {
        val indexOfRemoval = users.indexOfFirst { it.ip == ip }
        if (indexOfRemoval != -1) {
            users.removeAt(indexOfRemoval)
            notifyItemRemoved(indexOfRemoval)
        }
    }
}