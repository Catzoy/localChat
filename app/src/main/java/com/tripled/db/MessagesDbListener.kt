package com.tripled.db

import com.tripled.communication.client.ClientListener
import com.tripled.localChat.logic.Message
import com.tripled.utils.ChatsUtils

class MessagesDbListener(
    private val chatsDao: ChatsDao,
    private val messagesDao: MessagesDao
) : ClientListener {

    private val currentChatId = ChatsUtils.generateChatId()
    private var isChatActivated = false

    init {
        chatsDao.captureChat(currentChatId)
    }

    override fun onSocketClosed() {
        chatsDao.setChatStatusFinished(currentChatId)
    }

    override fun onNewMessage(message: Message) {
        if (!isChatActivated) {
            isChatActivated = true
            chatsDao.setChatStatusOngoing(message.chatId)
        }
        messagesDao.addMessage(message)
    }

    override fun onMessageSubmitted(message: Message) {
        messagesDao.addMessage(message)
    }

    override fun onMessageWritten() {
    }
}