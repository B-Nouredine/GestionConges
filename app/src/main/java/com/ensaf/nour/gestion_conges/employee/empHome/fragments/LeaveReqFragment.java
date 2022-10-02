package com.ensaf.nour.gestion_conges.employee.empHome.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.LeaveDao;
import com.ensaf.nour.gestion_conges.model.Leave;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LeaveReqFragment extends Fragment {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    LeaveDao leaveDao = new LeaveDao();
    EditText startDateEdit;
    EditText endDateEdit;
    Button saveBtn;
    Button cancelBtn;

    public LeaveReqFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leave_req, container, false);
        startDateEdit = view.findViewById(R.id.input_start_date);
        endDateEdit = view.findViewById(R.id.input_end_date);
        saveBtn = view.findViewById(R.id.save_leave_req_btn);
        cancelBtn = view.findViewById(R.id.cancel_leave_req_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInputs();
                startDateEdit.requestFocus();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = startDateEdit.getText().toString();
                String end = endDateEdit.getText().toString();

                Leave leave = new Leave();
                try {
                    leave.setStart(new SimpleDateFormat("dd/MM/yyyy").parse(start));
                    leave.setEnd(new SimpleDateFormat("dd/MM/yyyy").parse(end));
                    leave.setEmployeeID(auth.getCurrentUser().getUid());

                    leaveDao.add(leave);
                    Toast.makeText(getActivity(), "Leave Request Sent Successfully!", Toast.LENGTH_SHORT).show();
                    clearInputs();

                }catch (ParseException pe)
                {
                    Toast.makeText(getActivity(), "Please respect the date format", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void clearInputs()
    {
        startDateEdit.setText("");
        endDateEdit.setText("");
    }
}