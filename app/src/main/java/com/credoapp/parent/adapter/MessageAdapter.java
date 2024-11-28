package com.credoapp.parent.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.credoapp.parent.R;
import com.credoapp.parent.model.Messages;
import com.credoapp.parent.ui.MessagesScreen;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private MessagesScreen messagesScreen;
    private List<Messages> messagesList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(messagesScreen), R.layout.item_messages_layout, parent, false);
        View view = LayoutInflater.from(messagesScreen).inflate(R.layout.item_messages_layout, parent, false);
//        ItemMessageLayoutBinding binding = (ItemMessageLayoutBinding) viewDataBinding;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Messages messages = messagesList.get(position);
        holder.message_date_text.setText(messages.getDate());
        holder.message_description.setText(messages.getMessage());
        if (messages.getIdentity().equals("group")) {
            holder.message_icon.setImageResource(R.drawable.ic_message_multiple);
        } else {
            holder.message_icon.setImageResource(R.drawable.ic_message_single);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView message_icon;
        TextView message_date_text, message_description;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            message_icon = view.findViewById(R.id.message_icon);
            message_date_text = view.findViewById(R.id.message_date_text);
            message_description = view.findViewById(R.id.message_description);
        }
    }

    public MessageAdapter(MessagesScreen messagesScreen, List<Messages> messagesList) {
        this.messagesScreen = messagesScreen;
        this.messagesList = messagesList;
    }
}
