package com.tripled.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ChatsDao {

    @Query("SELECT * FROM messages WHERE chatId = :chatId")
    fun getChat(chatId: String)

    @Query("SELECT DISTINCT chatId FROM messages")
    fun getChatsList(): List<String>

    @Query("INSERT INTO chats (chatId, status) VALUES(:chatId, 0)")
    fun captureChat(chatId: String)

    @Query("UPDATE chats SET status = 1 WHERE chatId = :chatId")
    fun setChatStatusOngoing(chatId: String)

    @Query("UPDATE chats SET status = 2 WHERE chatId = :chatId")
    fun setChatStatusFinished(chatId: String)
}