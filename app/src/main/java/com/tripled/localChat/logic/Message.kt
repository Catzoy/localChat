package com.tripled.localChat.logic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String,
    val text: String,
    val chatId: String,
    val clientId: String,
    val time: Long
)