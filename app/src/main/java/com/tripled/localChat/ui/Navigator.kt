package com.tripled.localChat.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.tripled.localChat.R
import com.tripled.localChat.ui.chat.ChatFragment
import com.tripled.localChat.ui.chats.ChatsFragment
import com.tripled.localChat.ui.chatsNotAvailable.ChatNotAvailableFragment

class Navigator private constructor(private val activity: AppCompatActivity) {
    companion object {
        fun of(activity: AppCompatActivity): Navigator = Navigator(activity)
    }

    private val manager: FragmentManager
        get() = activity.supportFragmentManager

    fun showNoChat() = activity.runOnUiThread {
        manager.beginTransaction()
            .replace(R.id.container, ChatNotAvailableFragment.newInstance())
            .commitNow()
    }

    fun showAvailableUsers() = activity.runOnUiThread {
        manager.beginTransaction()
            .replace(R.id.container, ChatsFragment.newInstance())
            .commitNow()
    }

    fun showArchievedChats() {

    }

    fun showChat(user: String) {
        manager.beginTransaction()
            .replace(R.id.container, ChatFragment.newInstance(user))
            .commit()
    }
}