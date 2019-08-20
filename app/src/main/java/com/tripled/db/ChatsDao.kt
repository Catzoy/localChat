package com.tripled.db

import androidx.room.Dao
import androidx.room.Query
import com.tripled.localChat.logic.Chat
import com.tripled.localChat.logic.Message

@Dao
interface ChatsDao {

    @Query("SELECT * FROM messages WHERE chatId = :chatId")
    fun getChat(chatId: String): List<Message>

    @Query("SELECT DISTINCT chatId FROM messages")
    fun getChatsList(): List<String>

    @Query("INSERT INTO chats (chatId, status) VALUES(:chatId, ${Chat.CAPTURED})")
    fun captureChat(chatId: String)

    @Query("UPDATE chats SET status = ${Chat.ONGOING} WHERE chatId = :chatId")
    fun setChatStatusOngoing(chatId: String)

    @Query("UPDATE chats SET status = ${Chat.FINISHED} WHERE chatId = :chatId")
    fun setChatStatusFinished(chatId: String)
}