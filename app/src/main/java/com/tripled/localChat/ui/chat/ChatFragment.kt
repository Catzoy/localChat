package com.tripled.localChat.ui.chat

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripled.communication.persistant.DataReceiver
import com.tripled.communication.persistant.SocketService
import com.tripled.localChat.R
import com.tripled.localChat.logic.User

class ChatFragment : Fragment(), DataReceiver {
    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var chatReceiver: ChatReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.chat_fragment, container, false).also { view ->
            view.findViewById<RecyclerView>(R.id.users_rv).apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = ChatsRvAdapter().also { chatReceiver = it }
            }
        }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        SocketService.registerDataReceiver(this)
    }

    override fun onDetach() {
        super.onDetach()
        SocketService.unregisterDataReceiver()
    }

    // Data receiver

    override fun onServerDown() {
        Toast.makeText(context, "Cannot establish connections", Toast.LENGTH_LONG).show()
    }

    override fun onNewMessage(ip: String) {
        activity?.runOnUiThread { chatReceiver.userNewMessage(ip) }
    }

    override fun onNewConnection(user: User) {
        activity?.runOnUiThread { chatReceiver.userFound(user) }
    }
}
