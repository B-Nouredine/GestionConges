package com.ensaf.nour.gestion_conges.admin.leaveReq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.ensaf.nour.gestion_conges.dao.LeaveDao;
import com.ensaf.nour.gestion_conges.employee.leaveReq.LeaveRequestUnit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminLeaveRequestsActivity extends AppCompatActivity implements LeaveReqOnClickListener{

    RecyclerView recyclerView;
    final List<LeaveRequestUnit> leaveRequestUnits = new ArrayList<>();
    AdminLeaveRequestAdapter leaveRequestsAdapter;
    ProgressBar progressBar;
    TextView title;

    EmployeeDao employeeDao;
    LeaveDao leaveDao;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests);

        recyclerView = findViewById(R.id.leave_requests_RecyclerView);
        progressBar = findViewById(R.id.leave_req_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        title = findViewById(R.id.leave_req_title);
        title.setText(R.string.leave_requests);


        leaveRequestsAdapter = new AdminLeaveRequestAdapter(AdminLeaveRequestsActivity.this, leaveRequestUnits, this);
        recyclerView.setAdapter(leaveRequestsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminLeaveRequestsActivity.this));
        recyclerView.setHasFixedSize(true);

        firebaseFirestore.collection("Leave").orderBy("employeeID").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                leaveRequestUnits.clear();
                if(value != null)
                {
                    for(QueryDocumentSnapshot snapshot: value)
                    {
                        if(snapshot.exists())
                        {
                            employeeDao = new EmployeeDao();
                            employeeDao.get((String) snapshot.getData().get("employeeID")).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot document) {
                                    if(document.exists())
                                    {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                        String startDate = simpleDateFormat.format( snapshot.getTimestamp("start").toDate());
                                        String endDate = simpleDateFormat.format(snapshot.getTimestamp("end").toDate());
                                        boolean answered = (boolean) snapshot.getData().get("answered");
                                        boolean accepted = (boolean) snapshot.getData().get("accepted");
                                        String leaveID = (String)  snapshot.getId();
                                        String employeeName =  document.getString("firstName") + " " + document.getString("lastName");

                                        leaveRequestUnits.add(new LeaveRequestUnit(startDate, endDate, answered, accepted, employeeName, leaveID));
                                        ((AdminLeaveRequestAdapter) recyclerView.getAdapter()).setLeaveRequestUnits(leaveRequestUnits);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.leave_req_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onAccept(int pos) {
        leaveDao = new LeaveDao();
        Map<String, Object> leave = new HashMap<>();
        leave.put("answered", true);
        leave.put("accepted", true);
        String leaveID = this.leaveRequestUnits.get(pos).getLeaveID();
        leaveDao.update(leaveID, leave);
        this.leaveRequestsAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onReject(int pos) {
        leaveDao = new LeaveDao();
        Map<String, Object> leave = new HashMap<>();
        leave.put("answered", true);
        leave.put("accepted", false);
        String leaveID = this.leaveRequestUnits.get(pos).getLeaveID();
        leaveDao.update(leaveID, leave);
        this.leaveRequestsAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onCancel(int pos) {
        leaveDao = new LeaveDao();
        Map<String, Object> leave = new HashMap<>();
        leave.put("answered", false);
        leave.put("accepted", false);
        String leaveID = this.leaveRequestUnits.get(pos).getLeaveID();
        leaveDao.update(leaveID, leave);
        this.leaveRequestsAdapter.notifyItemChanged(pos);
    }
}