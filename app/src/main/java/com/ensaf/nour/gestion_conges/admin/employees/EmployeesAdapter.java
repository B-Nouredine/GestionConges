package com.ensaf.nour.gestion_conges.admin.employees;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.messages.AdapterItemOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.MyViewHolder> {

    List<EmployeeUnit> employeeUnits;
    Context context;
    EmployeeOnClickListener onClickListener;

    public EmployeesAdapter(List<EmployeeUnit> employeeUnits, Context context, EmployeeOnClickListener onClickListener) {
        this.employeeUnits = employeeUnits;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EmployeesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.employees_adapter_layout, null);

        return new MyViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EmployeeUnit employeeUnit = employeeUnits.get(position);

        holder.fullName.setText(employeeUnit.getFirstName() + " " + employeeUnit.getLastName());
        holder.username.setText(employeeUnit.getUsername());
        holder.phone.setText(employeeUnit.getPhone());
        holder.mission.setText(employeeUnit.getMission());
        holder.startDate.setText(employeeUnit.getStartDate());
        holder.update.setImageResource(R.drawable.update);
        holder.delete.setImageResource(R.drawable.delete);
    }


    @Override
    public int getItemCount() {
        return employeeUnits.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullName;
        TextView username;
        TextView mission;
        TextView startDate;
        TextView phone;
        ImageView update;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView, EmployeeOnClickListener onClickListener) {
            super(itemView);

            fullName = itemView.findViewById(R.id.ea_emp_full_name);
            username = itemView.findViewById(R.id.ea_emp_username);
            mission = itemView.findViewById(R.id.ea_emp_mission);
            startDate = itemView.findViewById(R.id.ea_emp_start_date);
            phone = itemView.findViewById(R.id.ea_emp_phone_nb);
            update = itemView.findViewById(R.id.ea_update_emp);
            delete = itemView.findViewById(R.id.ea_delete_emp);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            onClickListener.onDeleteClicked(pos);
                        }
                    }
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            onClickListener.onUpdateClicked(pos);
                        }
                    }
                }
            });
        }
    }

}
