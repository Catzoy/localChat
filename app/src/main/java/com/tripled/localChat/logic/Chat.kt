package com.tripled.localChat.logic

import androidx.annotation.IntDef
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
class Chat(
    @PrimaryKey val chatId: String,
    @ChatStatus val status: Int = CAPTURED
) {

    // Using IntDef instead of enums
    companion object {
        const val CAPTURED = 0
        const val ONGOING = 1
        const val FINISHED = 2

        @IntDef(CAPTURED, ONGOING, FINISHED)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ChatStatus
    }
}