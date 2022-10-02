package com.ensaf.nour.gestion_conges.employee.empHome.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ensaf.nour.gestion_conges.MainActivity;
import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.ensaf.nour.gestion_conges.employee.attachments.AttachmentsActivity;
import com.ensaf.nour.gestion_conges.employee.leaveReq.LeaveRequestsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private LinearLayout leaveRequests;
    private LinearLayout attachments;
    private TextView username;
    private Button logoutBtn;
    private ImageView profilePic;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    EmployeeDao employeeDao = new EmployeeDao();


    public ProfileFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        leaveRequests = view.findViewById(R.id.leave_requests);
        attachments = view.findViewById(R.id.attachments);
        username = view.findViewById(R.id.profile_username);
        logoutBtn = view.findViewById(R.id.emp_logout);
        profilePic = view.findViewById(R.id.profile_pic_home);

        username.setText(auth.getCurrentUser().getEmail());

        leaveRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LeaveRequestsActivity.class);
                startActivity(intent);
            }
        });

        employeeDao.get(auth.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot emp) {
                if(emp.exists())
                {
                    if(emp.get("profilePic") != null && ! emp.get("profilePic").equals(""))
                        Picasso.get().load((String) emp.get("profilePic")).into(profilePic);
                }
            }
        });

        attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AttachmentsActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutEmployee();
            }
        });

        return view;
    }

    public void logoutEmployee()
    {
        auth.signOut();
        this.getActivity().finish();
        startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}