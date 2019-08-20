package com.tripled.localChat.ui

import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.tripled.localChat.R
import com.tripled.localChat.ui.chat.ChatFragment
import com.tripled.localChat.ui.chats.ChatsFragment
import com.tripled.localChat.ui.chatsNotAvailable.ChatNotAvailableFragment

class Navigator private constructor(private val activity: AppCompatActivity) {
    companion object {
        const val STATE_MAIN_SCREEN = 0
        const val STATE_CHAT_SCREEN = 1

        @IntDef(STATE_MAIN_SCREEN, STATE_CHAT_SCREEN)
        @Retention(AnnotationRetention.SOURCE)
        annotation class NavigatorState

        @NavigatorState
        var appState: Int = STATE_MAIN_SCREEN

        fun of(activity: AppCompatActivity): Navigator = Navigator(activity)
    }

    private val manager: FragmentManager
        get() = activity.supportFragmentManager

    fun showNoChat() = activity.runOnUiThread {
        appState = STATE_MAIN_SCREEN
        manager.beginTransaction()
            .replace(R.id.container, ChatNotAvailableFragment.newInstance())
            .commitNow()
    }

    fun showAvailableUsers() = activity.runOnUiThread {
        appState = STATE_MAIN_SCREEN
        manager.beginTransaction()
            .replace(R.id.container, ChatsFragment.newInstance())
            .commitNow()
    }

    fun showArchievedChats() {

    }

    fun showChat(user: String) = activity.runOnUiThread {
        appState = STATE_CHAT_SCREEN
        manager.beginTransaction()
            .replace(R.id.container, ChatFragment.newInstance(user))
            .commit()
    }
}