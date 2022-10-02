package com.ensaf.nour.gestion_conges.messages;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensaf.nour.gestion_conges.R;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<MessagesUnit> messagesUnits;
    private final AdapterItemOnClickListener messageItemOnclickListener;
    private final Context context;

    public MessagesAdapter(List<MessagesUnit> messagesUnits, Context context, AdapterItemOnClickListener messageItemOnclickListener) {
        this.messagesUnits = messagesUnits;
        this.context = context;
        this.messageItemOnclickListener = messageItemOnclickListener;
    }

    public void setMessagesUnits(List<MessagesUnit> messagesUnits) {
        this.messagesUnits = messagesUnits;
        this.notifyDataSetChanged();
    }

    public List<MessagesUnit> getMessagesUnits() {
        return messagesUnits;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messages_adapter_layout, null);



        return new MyViewHolder(view, messageItemOnclickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        MessagesUnit messagesUnit = messagesUnits.get(position);
        holder.senderName.setText(messagesUnit.getEmployeeName());
        holder.lastMessage.setText(messagesUnit.getLastMessage());
        holder.mission.setText(messagesUnit.getEmployeeMission());
        holder.senderProfilePic.setImageResource(R.drawable.ic_profile);

        if(messagesUnit.getEmployeeMission().equals("admin"))
        {
            holder.mission.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return messagesUnits.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView senderName;
        private TextView lastMessage;
        private TextView mission;
        private ImageView senderProfilePic;

        public MyViewHolder(@NonNull View itemView, AdapterItemOnClickListener messageItemOnclickListener) {
            super(itemView);

            senderName = itemView.findViewById(R.id.message_sender_name);
            lastMessage = itemView.findViewById(R.id.last_message);
            mission = itemView.findViewById(R.id.messages_mission);
            senderProfilePic = itemView.findViewById(R.id.message_sender_profile_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messageItemOnclickListener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            messageItemOnclickListener.onItemClicked(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }
}
