package com.tripled.localChat.logic

import android.graphics.Color
import com.tripled.localChat.logic.User.UserState.*

data class User(
    val id: String,
    val state: UserState,
    val ip: String,
    val name: String? = null
) {
    enum class UserState {
        Unavailable,
        Available,
        Connected,
        HasMessages
    }

    constructor(id: String, ip: String, name: String? = null) : this(id, Available, ip, name)

    val colorForState: Int
        get() = when (state) {
            Unavailable -> Color.RED
            Available -> Color.CYAN
            Connected -> Color.GREEN
            HasMessages -> Color.BLUE
        }
}