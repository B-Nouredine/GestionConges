package com.ensaf.nour.gestion_conges.employee.leaveReq;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ensaf.nour.gestion_conges.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveRequestsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    final List<LeaveRequestUnit> leaveRequestUnits = new ArrayList<>();
    LeaveRequestsAdapter leaveRequestsAdapter;
    ProgressBar progressBar;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests);

        recyclerView = findViewById(R.id.leave_requests_RecyclerView);
        progressBar = findViewById(R.id.leave_req_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        firebaseFirestore.collection("Leave").whereEqualTo("employeeID", auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                leaveRequestUnits.clear();
                if(value != null)
                {
                    for(QueryDocumentSnapshot snapshot: value)
                    {
                        if(snapshot.exists())
                        {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            String startDate = simpleDateFormat.format( snapshot.getTimestamp("start").toDate());
                            String endDate = simpleDateFormat.format(snapshot.getTimestamp("end").toDate());
                            boolean answered = (boolean) snapshot.getData().get("answered");
                            boolean accepted = (boolean) snapshot.getData().get("accepted");

                            leaveRequestUnits.add(new LeaveRequestUnit(startDate, endDate, answered, accepted));
                        }
                    }

                    leaveRequestsAdapter = new LeaveRequestsAdapter(LeaveRequestsActivity.this, leaveRequestUnits);
                    recyclerView.setAdapter(leaveRequestsAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(LeaveRequestsActivity.this));
        recyclerView.setHasFixedSize(true);

        findViewById(R.id.leave_req_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}