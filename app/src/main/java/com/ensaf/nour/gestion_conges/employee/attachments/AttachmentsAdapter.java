package com.ensaf.nour.gestion_conges.employee.attachments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.messages.AdapterItemOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.MyViewHolder> {

    final Context context;
    final List<AttachmentUnit> attachmentUnits;
    final AdapterItemOnClickListener attachmentsOnClickListener;

    public AttachmentsAdapter(Context context, List<AttachmentUnit> attachmentUnits, AdapterItemOnClickListener attachmentsOnClickListener) {
        this.context = context;
        this.attachmentUnits = attachmentUnits;
        this.attachmentsOnClickListener = attachmentsOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attachments_adapter_layout, null);
        return new MyViewHolder(view, attachmentsOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AttachmentUnit attachmentUnit = attachmentUnits.get(position);

        Picasso.get().load(attachmentUnit.getAttachmentUri()).into(holder.attachmentPreview);
        String name = attachmentUnit.getAttachmentName();
        if(name.length() > 24)
        {
            name = name.substring(0, 24) + "...";
        }
        holder.attachmentName.setText(name);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(attachmentUnit.getAttachmentDate());
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        holder.attachmentDate.setText(""+mDay+"/"+mMonth+"/"+mYear);

        if(attachmentUnit.isShowPreview())
        {
            holder.attachmentPreview.setVisibility(View.VISIBLE);
            holder.arrowPreview.setImageResource(R.drawable.ic_arrow_ud_filled);
        }
        else
        {
            holder.arrowPreview.setImageResource(R.drawable.ic_arrow_lr_filled);
            holder.attachmentPreview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return attachmentUnits.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout relativeLayout;
        private final TextView attachmentName;
        private final TextView attachmentDate;
        private final ImageView attachmentPreview;
        private final ImageView arrowPreview;

        public MyViewHolder(@NonNull View itemView, AdapterItemOnClickListener attachmentsOnClickListener) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.aa_relativeLayout);
            attachmentName = itemView.findViewById(R.id.aa_attachment_name);
            attachmentDate = itemView.findViewById(R.id.aa_attachment_date);
            attachmentPreview = itemView.findViewById(R.id.aa_attachment_preview);
            arrowPreview = itemView.findViewById(R.id.aa_arrow_lr);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(attachmentsOnClickListener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            attachmentsOnClickListener.onItemClicked(pos);
                        }
                    }
                }
            });
        }

    }
}
