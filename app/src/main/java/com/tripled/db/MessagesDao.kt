package com.tripled.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.tripled.localChat.logic.Message

@Dao
interface MessagesDao {

    @Insert
    fun addMessage(message: Message)

    @Update
    fun updateMessage(message: Message)
}