package com.ensaf.nour.gestion_conges.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensaf.nour.gestion_conges.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private final List<ChatUnit> chatUnits;
    private final Context context;

    public ChatAdapter(List<ChatUnit> chatUnits, Context context) {
        this.chatUnits = chatUnits;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        ChatUnit chatUnit = chatUnits.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(chatUnit.getDateMs());
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mHour = calendar.get(Calendar.HOUR);
        int mMin = calendar.get(Calendar.MINUTE);
        String mAM_PM = calendar.get(Calendar.AM_PM) == 0? "AM" : "PM";

        if(Objects.equals(chatUnit.getUserSource(), auth.getCurrentUser().getUid()))
        {
            holder.oppLayout.setVisibility(View.GONE);
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.myTitle.setText(chatUnit.getTitle());
            holder.myContent.setText(chatUnit.getMessage());
            holder.myDate.setText(""+mDay+"-"+mMonth+"-"+mYear+" "+mHour+":"+mMin+" "+mAM_PM);
        }
        else
        {
            holder.oppLayout.setVisibility(View.VISIBLE);
            holder.myLayout.setVisibility(View.GONE);
            holder.oppTitle.setText(chatUnit.getTitle());
            holder.oppContent.setText(chatUnit.getMessage());
            holder.oppDate.setText(""+mDay+"-"+mMonth+"-"+mYear+" "+mHour+":"+mMin+" "+mAM_PM);
        }


    }

    @Override
    public int getItemCount() {
        return chatUnits.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout oppLayout, myLayout;
        private TextView oppTitle, myTitle;
        private TextView oppContent, myContent;
        private TextView oppDate, myDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            oppLayout = itemView.findViewById(R.id.opp_layout);
            oppTitle = itemView.findViewById(R.id.opp_chat_title);
            oppContent = itemView.findViewById(R.id.opp_chat_content);
            oppDate = itemView.findViewById(R.id.opp_chat_date);

            myLayout = itemView.findViewById(R.id.my_layout);
            myTitle = itemView.findViewById(R.id.my_chat_title);
            myContent = itemView.findViewById(R.id.my_chat_content);
            myDate = itemView.findViewById(R.id.my_chat_date);
        }
    }
}
