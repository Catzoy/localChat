package com.tripled.localChat.ui.chatsNotAvailable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tripled.localChat.R

class ChatNotAvailableFragment : Fragment() {
    companion object {
        fun newInstance() = ChatNotAvailableFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_chat_not_available, container, false)
}
