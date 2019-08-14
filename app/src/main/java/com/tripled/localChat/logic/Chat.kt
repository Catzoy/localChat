package com.tripled.localChat.logic

import androidx.room.Entity
import androidx.room.TypeConverters

@Entity(tableName = "chats")
data class Chat(
    val chatId: String,
    @TypeConverters(ChatStatus::class)
    val status: ChatStatus = ChatStatus.Captured
) {
    enum class ChatStatus(val code: Int) {
        Captured(0),
        Ongoing(1),
        Finished(2)
    }
}