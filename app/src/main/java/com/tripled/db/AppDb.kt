package com.tripled.db

import androidx.room.RoomDatabase

abstract class AppDb : RoomDatabase() {
    abstract fun chatsDao(): ChatsDao
    abstract fun messagesDao(): MessagesDao
}