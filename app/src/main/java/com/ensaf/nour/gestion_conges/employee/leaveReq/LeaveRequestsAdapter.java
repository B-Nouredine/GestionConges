package com.ensaf.nour.gestion_conges.employee.leaveReq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensaf.nour.gestion_conges.R;

import java.util.List;

public class LeaveRequestsAdapter extends RecyclerView.Adapter<LeaveRequestsAdapter.MyViewHolder> {

    Context context;
    List<LeaveRequestUnit> leaveRequestUnits;

    public LeaveRequestsAdapter(Context context, List<LeaveRequestUnit> leaveRequestUnits) {
        this.context = context;
        this.leaveRequestUnits = leaveRequestUnits;
    }

    @NonNull
    @Override
    public LeaveRequestsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leave_req_adapter_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveRequestsAdapter.MyViewHolder holder, int position) {
            LeaveRequestUnit leaveRequestUnit = leaveRequestUnits.get(position);

            holder.startDate.setText(leaveRequestUnit.getStartDate());
            holder.endDate.setText(leaveRequestUnit.getEndDate());
            holder.arrowLR.setImageResource(R.drawable.ic_arrow_lr);

            if(!leaveRequestUnit.isAnswered())
            {
                holder.statusImage.setImageResource(R.drawable.pending);
                holder.status.setText(R.string.pending);
            }
            else
            {
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            startDate = itemView.findViewById(R.id.lra_start_date);
            endDate = itemView.findViewById(R.id.lra_end_date);
            status = itemView.findViewById(R.id.lra_status);
            statusImage = itemView.findViewById(R.id.lra_status_image);
            arrowLR = itemView.findViewById(R.id.lra_arrow_lr);
        }
    }
}
