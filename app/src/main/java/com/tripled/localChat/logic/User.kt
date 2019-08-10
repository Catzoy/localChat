package com.tripled.localChat.logic

import android.graphics.Color
import com.tripled.localChat.logic.User.UserState.*

data class User(
    val id: String,
    val state: UserState,
    val ip: String,
    val name: String? = null
) {
    companion object {
        fun copy(user: User, id: String? = null, state: UserState? = null, ip: String? = null, name: String? = null) =
            User(id ?: user.id, state ?: user.state, ip ?: user.ip, name ?: user.name)
    }

    enum class UserState {
        Unavailable,
        Available,
        Connected,
        HasMessages
    }

    constructor(id: String, ip: String, name: String? = null) : this(id, Available, ip, name)

    val colorForState: Int
        get() = when (state) {
            Unavailable -> Color.GRAY
            Available -> Color.RED
            Connected -> Color.GREEN
            HasMessages -> Color.BLUE
        }
}