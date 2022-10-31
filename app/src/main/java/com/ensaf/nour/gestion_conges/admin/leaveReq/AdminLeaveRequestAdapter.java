package com.ensaf.nour.gestion_conges.admin.leaveReq;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.employee.leaveReq.LeaveRequestUnit;

import java.util.List;

public class AdminLeaveRequestAdapter extends RecyclerView.Adapter<AdminLeaveRequestAdapter.MyViewHolder>{

    Context context;
    List<LeaveRequestUnit> leaveRequestUnits;
    LeaveReqOnClickListener leaveReqOnClickListener;

    public AdminLeaveRequestAdapter(Context context, List<LeaveRequestUnit> leaveRequestUnits, LeaveReqOnClickListener leaveReqOnClickListener) {
        this.context = context;
        this.leaveRequestUnits = leaveRequestUnits;
        this.leaveReqOnClickListener = leaveReqOnClickListener;
    }

    public void setLeaveRequestUnits(List<LeaveRequestUnit> leaveRequestUnits) {
        this.leaveRequestUnits = leaveRequestUnits;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leave_req_adapter_layout, null);
        return new MyViewHolder(view, this.leaveReqOnClickListener, this.leaveRequestUnits);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LeaveRequestUnit leaveRequestUnit = leaveRequestUnits.get(position);

        holder.empName.setText(leaveRequestUnit.getEmployeeName());
        holder.startDate.setText(leaveRequestUnit.getStartDate());
        holder.endDate.setText(leaveRequestUnit.getEndDate());
        holder.arrowLR.setImageResource(R.drawable.ic_arrow_lr);

        if(!leaveRequestUnit.isAnswered())
        {
            holder.statusImage.setImageResource(R.drawable.pending);
            holder.status.setText(R.string.pending);
            holder.cancel.setEnabled(false);
            holder.cancel.setBackgroundColor(Color.GRAY);
            holder.accept.setEnabled(true);
            holder.accept.setBackgroundColor(Color.rgb(0, 255, 0));
            holder.reject.setEnabled(true);
            holder.reject.setBackgroundColor(Color.rgb(255, 0, 0));
        }
        else
        {
            holder.cancel.setEnabled(true);
            holder.cancel.setBackgroundColor(Color.rgb(255, 87, 34));
            holder.accept.setEnabled(false);
            holder.accept.setBackgroundColor(Color.GRAY);
            holder.reject.setEnabled(false);
            holder.reject.setBackgroundColor(Color.GRAY);

            if(leaveRequestUnit.isAccepted())
            {
                holder.statusImage.setImageResource(R.drawable.accepted);
                holder.status.setText(R.string.accepted);
            }
            else
            {
                holder.statusImage.setImageResource(R.drawable.rejected);
                holder.status.setText(R.string.rejected);
            }
        }
    }

    @Override
    public int getItemCount() {
        return leaveRequestUnits.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView startDate;
        private TextView endDate;
        private TextView status;
        private ImageView statusImage;
        private ImageView arrowLR;
        private RelativeLayout layout;
        private Button cancel;
        private Button accept;
        private Button reject;
        private TextView empName;

        public MyViewHolder(@NonNull View itemView, LeaveReqOnClickListener leaveReqOnClickListener, List<LeaveRequestUnit> leaveRequestUnits) {
            super(itemView);
            startDate = itemView.findViewById(R.id.lra_start_date);
            endDate = itemView.findViewById(R.id.lra_end_date);
            status = itemView.findViewById(R.id.lra_status);
            statusImage = itemView.findViewById(R.id.lra_status_image);
            arrowLR = itemView.findViewById(R.id.lra_arrow_lr);

            layout = itemView.findViewById(R.id.lra_layout_btns);
            layout.setVisibility(View.VISIBLE);
            cancel = itemView.findViewById(R.id.lra_cancel_btn);
            accept = itemView.findViewById(R.id.lra_accept_btn);
            reject = itemView.findViewById(R.id.lra_reject_btn);
            empName = itemView.findViewById(R.id.lra_empName);
            empName.setVisibility(View.VISIBLE);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leaveReqOnClickListener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            if(leaveRequestUnits.get(pos) != null)
                                leaveReqOnClickListener.onAccept(pos);
                        }
                    }
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leaveReqOnClickListener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            if(leaveRequestUnits.get(pos) != null)
                                leaveReqOnClickListener.onReject(pos);
                        }
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leaveReqOnClickListener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            if(leaveRequestUnits.get(pos) != null)
                                leaveReqOnClickListener.onCancel(pos);
                        }
                    }
                }
            });
        }

}

}
