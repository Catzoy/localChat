package com.tripled.localChat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripled.localChat.R
import kotlinx.android.synthetic.main.chat_fragment.view.*

class ChatFragment : Fragment() {

    companion object {

        private const val KEY_USER_ID = "user_id"

        fun newInstance(userId: String): ChatFragment {
            val bundle = Bundle()
            bundle.putString(KEY_USER_ID, userId)
            return ChatFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.chat_fragment, container, false).also { view ->
            view.rv_messages.apply {
                layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
                adapter = ChatRvAdapter()
            }
        }
}