package com.ensaf.nour.gestion_conges.admin.employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class UpdateEmpActivity extends AppCompatActivity {

    TextView firstName;
    TextView lastName;
    TextView mission;
    TextView phone;
    TextView startDate;
    TextView title;
    Button updateBtn;
    Button cancelBtn;
    TextInputLayout username;
    TextInputLayout password;

    EmployeeDao employeeDao;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_emp);

        title = findViewById(R.id.newEmp_add_updateTV);
        firstName = findViewById(R.id.newEmp_first_name);
        lastName = findViewById(R.id.newEmp_last_name);
        mission = findViewById(R.id.newEmp_mission);
        phone = findViewById(R.id.newEmp_phone);
        startDate = findViewById(R.id.newEmp_start_date);
        updateBtn = findViewById(R.id.newEmp_add_btn);
        cancelBtn = findViewById(R.id.newEmp_cancel_btn);
        username = findViewById(R.id.newEmp_username_parent);
        password = findViewById(R.id.newEmp_password_parent);

        title.setText(R.string.update_employee);
        updateBtn.setText(R.string.update);
        password.setVisibility(View.GONE);
        username.setVisibility(View.GONE);

        Bundle empData = this.getIntent().getExtras();
        if(empData != null)
        {
            firstName.setText(empData.getString("firstName"));
            lastName.setText(empData.getString("lastName"));
            mission.setText(empData.getString("mission"));
            phone.setText(empData.getString("phone"));
            startDate.setText(empData.getString("startDate"));
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFieldsAreSet())
                {
                    employeeDao = new EmployeeDao();
                    Map<String, Object> empUpdated = new HashMap<>();
                    empUpdated.put("firstName", firstName.getText().toString().trim());
                    empUpdated.put("lastName", lastName.getText().toString().trim());
                    empUpdated.put("mission", mission.getText().toString().trim());
                    empUpdated.put("phone", phone.getText().toString().trim());
                    try {
                        empUpdated.put("startDate", new SimpleDateFormat("dd/MM/yyyy").parse(startDate.getText().toString().trim()));
                    } catch (ParseException e) {
                        startDate.requestFocus();
                        startDate.setError("");
                        Toast.makeText(UpdateEmpActivity.this, "Please respect the date format!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    assert empData != null;
                    employeeDao.update(empData.getString("id"), empUpdated).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UpdateEmpActivity.this, "Employee updated!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateEmpActivity.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private boolean allFieldsAreSet() {
        if (firstName.getText().toString().trim().isEmpty()) {
            firstName.setError("Required Field");
            firstName.requestFocus();
            return false;
        }
        if (lastName.getText().toString().trim().isEmpty()) {
            lastName.setError("Required Field");
            lastName.requestFocus();
            return false;
        }
        if (mission.getText().toString().trim().isEmpty()) {
            mission.setError("Required Field");
            mission.requestFocus();
            return false;
        }
        if (phone.getText().toString().trim().isEmpty()) {
            phone.setError("Required Field");
            phone.requestFocus();
            return false;
        }
        if (startDate.getText().toString().trim().isEmpty()) {
            startDate.setError("Required Field");
            startDate.requestFocus();
            return false;
        }

        return true;
    }
}