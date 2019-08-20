package com.tripled.localChat.logic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tripled.utils.ChatsUtils

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String = ChatsUtils.generateMessageId(),
    val text: String,
    val chatId: String,
    val clientId: String,
    val time: Long = System.currentTimeMillis()
)