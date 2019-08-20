package com.tripled.utils

object ChatsUtils {

    private const val DEFAULT_CHAT_ID_LENGTH = 5
    private const val DEFAULT_MESSAGE_ID_LENGTH = 10

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generateChatId(): String {
        return (1..DEFAULT_CHAT_ID_LENGTH)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun generateMessageId(): String {
        return (1..DEFAULT_MESSAGE_ID_LENGTH)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}