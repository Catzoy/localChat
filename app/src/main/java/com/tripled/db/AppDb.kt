package com.tripled.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tripled.localChat.logic.Chat
import com.tripled.localChat.logic.Message

@Database(entities = [Chat::class, Message::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun chatsDao(): ChatsDao
    abstract fun messagesDao(): MessagesDao
}