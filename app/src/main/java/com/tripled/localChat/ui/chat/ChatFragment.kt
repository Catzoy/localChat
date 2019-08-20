package com.tripled.localChat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripled.communication.persistant.SocketService
import com.tripled.localChat.R
import com.tripled.localChat.logic.Message
import kotlinx.android.synthetic.main.chat_fragment.view.*

class ChatFragment : Fragment() {

    companion object {

        private const val KEY_USER_ID = "user_id"
        private const val KEY_CHAT_ID = "chat_id"

        fun newInstance(userId: String, chatId: String): ChatFragment {
            val bundle = Bundle()
            bundle.putString(KEY_USER_ID, userId)
            bundle.putString(KEY_CHAT_ID, chatId)
            return ChatFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.chat_fragment, container, false).also { view ->
            val args = arguments
            if (args == null) {
                Toast.makeText(activity, "Cannot get data", Toast.LENGTH_SHORT).show()
                return view
            }

            val userId = args.getString(KEY_USER_ID)!!
            val chatId = args.getString(KEY_CHAT_ID)!!

            val chatRvAdapter = ChatRvAdapter()
            view.rv_messages.apply {
                layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
                adapter = chatRvAdapter
            }

            view.submit_message_btn.setOnClickListener {
                val text = view.message_input.text.toString()
                view.message_input.text.clear()
                chatRvAdapter.addMessage(text)
                val message = Message(
                    text = text,
                    chatId = chatId,
                    clientId = userId
                )
                SocketService.sendMessage(message)
            }
        }
}