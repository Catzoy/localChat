package com.tripled.localChat.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripled.localChat.R
import kotlinx.android.synthetic.main.message_view_holder.view.*

class ChatRvAdapter : RecyclerView.Adapter<ChatRvAdapter.MessageView>() {
    private val messages = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_view_holder, parent, false)
        return MessageView(view)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: MessageView, position: Int) {
        holder.setText(messages[position])
    }

    inner class MessageView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.message_text

        fun setText(message: String) {
            textView.text = message
        }
    }
}