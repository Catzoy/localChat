package com.tripled.localChat.ui.chats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripled.communication.persistant.DataReceiver
import com.tripled.communication.persistant.SocketService
import com.tripled.localChat.R
import com.tripled.localChat.logic.User
import com.tripled.localChat.ui.Navigator
import kotlinx.android.synthetic.main.chats_fragment.view.*

class ChatsFragment : Fragment(), DataReceiver, ChatsInputListener {
    companion object {
        fun newInstance() = ChatsFragment()
    }

    private lateinit var chatReceiver: ChatsReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.chats_fragment, container, false).also { view ->
            view.users_rv.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = ChatsRvAdapter(this@ChatsFragment).also { chatReceiver = it }
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

    // region Data receiver

    override fun onServerDown() {
        Toast.makeText(context, "Cannot establish connections", Toast.LENGTH_LONG).show()
    }

    override fun onNewMessage(ip: String) {
        activity?.runOnUiThread { chatReceiver.userNewMessage(ip) }
    }

    override fun onNewConnection(user: User) {
        activity?.runOnUiThread { chatReceiver.userFound(user) }
    }

    // endregion

    // region ChatsInputListener

    override fun onStartConversation(id: String) {
        Navigator.of(requireActivity() as AppCompatActivity).showChat(id)
    }

    // endregion
}
