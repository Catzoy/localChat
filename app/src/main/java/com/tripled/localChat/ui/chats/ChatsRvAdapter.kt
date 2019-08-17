package com.tripled.localChat.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripled.localChat.R
import com.tripled.localChat.logic.User
import kotlinx.android.synthetic.main.user_view_holder.view.*

class ChatsRvAdapter(
    private val listener: ChatsInputListener
) : RecyclerView.Adapter<ChatsRvAdapter.UserVIew>(), ChatsReceiver {
    inner class UserVIew(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName = itemView.user_name
        private val userIp = itemView.user_ip
        private val userStatus = itemView.status_circle
        private val userLayout = itemView.user_layout
        private lateinit var userId: String

        init {
            userLayout.setOnClickListener {
                listener.onStartConversation(userId)
            }
        }

        fun inherit(user: User) {
            val context = userName.context
            userId = user.id
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVIew {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_view_holder, parent, false)
        return UserVIew(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserVIew, position: Int) {
        holder.inherit(users[position])
    }

    override fun userFound(user: User) {
        val indexOfUser = users.indexOfFirst { it.id == user.id }
        if (indexOfUser != -1) {
            users[indexOfUser] = user
            notifyItemChanged(indexOfUser)
        } else {
            users.add(user)
            notifyItemInserted(users.size - 1)
        }
    }

    override fun userDisconnected(ip: String) {
        val indexOfRemoval = users.indexOfFirst { it.ip == ip }
        if (indexOfRemoval != -1) {
            users.removeAt(indexOfRemoval)
            notifyItemRemoved(indexOfRemoval)
        }
    }

    override fun userNewMessage(ip: String) {
        val indexOfUser = users.indexOfFirst { it.ip == ip }
        if (indexOfUser != -1) {
            users[indexOfUser] = User.copy(users[indexOfUser], state = User.UserState.HasMessages)
            notifyItemChanged(indexOfUser)
        }
    }
}