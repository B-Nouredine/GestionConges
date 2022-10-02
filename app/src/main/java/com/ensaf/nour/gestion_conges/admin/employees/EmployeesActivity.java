package com.ensaf.nour.gestion_conges.admin.employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.ensaf.nour.gestion_conges.employee.leaveReq.LeaveRequestsActivity;
import com.ensaf.nour.gestion_conges.messages.AdapterItemOnClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EmployeesActivity extends AppCompatActivity implements EmployeeOnClickListener {

    RecyclerView employeesRecyclerView;
    EmployeesAdapter employeesAdapter;
    List<EmployeeUnit> employeeUnits = new ArrayList<>();
    ProgressBar progressBar;
    Button addEmployeeBtn;
    final EmployeesActivity THIS = this;

    EmployeeDao employeeDao = new EmployeeDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        loadEmployeesFromDB();

        findViewById(R.id.admin_emp_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeesActivity.this, NewEmpActivity.class));
            }
        });
    }

    @Override
    public void onUpdateClicked(int pos) {
        EmployeeUnit emp = employeeUnits.get(pos);

        Intent intent = new Intent(EmployeesActivity.this, UpdateEmpActivity.class);
        intent.putExtra("id", emp.getEmployeeID());
        intent.putExtra("firstName", emp.getFirstName());
        intent.putExtra("lastName", emp.getLastName());
        intent.putExtra("username", emp.getUsername());
        intent.putExtra("mission", emp.getMission());
        intent.putExtra("phone", emp.getPhone());
        intent.putExtra("startDate", emp.getStartDate());

        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(EmployeesActivity.this);
        alert.setTitle("Delete Employee");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeDao.remove(employeeUnits.get(pos).getEmployeeID()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(EmployeesActivity.this, "Employee deleted!", Toast.LENGTH_SHORT).show();
                        THIS.finish();
                        startActivity(new Intent(EmployeesActivity.this, EmployeesActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EmployeesActivity.this, "Could not delete employee! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadEmployeesFromDB();
    }

    void loadEmployeesFromDB()
    {
        employeeUnits.clear();
        employeesRecyclerView = findViewById(R.id.admin_employeesRecyclerView);
        progressBar = findViewById(R.id.admin_emp_progress_bar);
        addEmployeeBtn = findViewById(R.id.admin_btn_addEmployee);
        progressBar.setVisibility(View.VISIBLE);

        employeeDao.getAll().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot emp: task.getResult())
                    {
                        if(emp.exists())
                        {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Timestamp startDate = emp.getTimestamp("startDate");
                            String startDateString = "";

                            if(startDate != null)
                            {
                                startDateString = simpleDateFormat.format(startDate.toDate());
                            }
                            employeeUnits.add(new EmployeeUnit((String) emp.getId(),"" + emp.get("firstName"),
                                    ""+emp.get("lastName"), ""+emp.get("phone"), ""+emp.get("mission"),
                                    ""+emp.get("username"), startDateString, (String) emp.get("profilePic")));
                        }
                    }

                    employeesAdapter = new EmployeesAdapter(employeeUnits, EmployeesActivity.this, THIS);
                    employeesRecyclerView.setAdapter(employeesAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        employeesRecyclerView.setLayoutManager(new LinearLayoutManager(EmployeesActivity.this));
        employeesRecyclerView.setHasFixedSize(true);
        employeesRecyclerView.setNestedScrollingEnabled(false);
        employeesRecyclerView.setVerticalScrollbarPosition(employeeUnits.size() -1);
    }
}