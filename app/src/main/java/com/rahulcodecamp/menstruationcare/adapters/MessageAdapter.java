package com.rahulcodecamp.menstruationcare.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.rahulcodecamp.menstruationcare.models.AllMethods;
import com.rahulcodecamp.menstruationcare.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.rahulcodecamp.menstruationcare.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    Context context;
    List<Message> messages;
    DatabaseReference messageDb;

    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messageDb) {
        this.context = context;
        this.messages = messages;
        this.messageDb = messageDb;
    }

    @NonNull
    @NotNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_recv_message, parent, false);
        return new MessageAdapterViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MessageAdapterViewHolder holder, int position) {

        Message message = messages.get(position);

        if (message.getName().equals(AllMethods.name)) {
            holder.userName.setText(R.string.you);
            holder.userName.setTextColor(R.color.green);
            holder.tvRecvMsg.setText(message.getMessage());
            holder.tvRecvMsg.setGravity(Gravity.START);
            holder.recvLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.userName.setText(message.getName());
            holder.tvRecvMsg.setText(message.getMessage());
            holder.tvRecvMsg.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView tvRecvMsg;
        ImageView btnDelete;
        FlexboxLayout recvLayout; // For sent
//        TextView tvSentMsg;

        public MessageAdapterViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            tvRecvMsg = (TextView) itemView.findViewById(R.id.tvRecvMsg);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
            recvLayout = (FlexboxLayout) itemView.findViewById(R.id.containerRLayout);

            btnDelete.setOnClickListener(v -> messageDb.child(messages.get(getAdapterPosition()).getKey()).removeValue());

        }
    }
}
