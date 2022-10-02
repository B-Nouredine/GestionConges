package com.ensaf.nour.gestion_conges.admin.employees;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.ensaf.nour.gestion_conges.model.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewEmpActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText mission;
    EditText phone;
    EditText startDate;
    Button addBtn;
    Button cancelBtn;

    EmployeeDao employeeDao = new EmployeeDao();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_emp);
        admin = auth.getCurrentUser();

        firstName = findViewById(R.id.newEmp_first_name);
        lastName = findViewById(R.id.newEmp_last_name);
        username = findViewById(R.id.newEmp_username);
        password = findViewById(R.id.newEmp_password);
        mission = findViewById(R.id.newEmp_mission);
        phone = findViewById(R.id.newEmp_phone);
        startDate = findViewById(R.id.newEmp_start_date);
        addBtn = findViewById(R.id.newEmp_add_btn);
        cancelBtn = findViewById(R.id.newEmp_cancel_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputs();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFieldsAreSet()) {
                    Date stDate;
                    try {
                        stDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate.getText().toString().trim());
                    } catch (ParseException e) {
                        startDate.requestFocus();
                        startDate.setError("");
                        Toast.makeText(NewEmpActivity.this, "Please respect the date format!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    auth.createUserWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString())
                            .addOnCompleteListener(NewEmpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String empId = task.getResult().getUser().getUid();
                                        Employee emp = new Employee(firstName.getText().toString().trim(), lastName.getText().toString().trim()
                                                , phone.getText().toString().trim(), mission.getText().toString().trim()
                                                , username.getText().toString().trim(), password.getText().toString().trim()
                                                , stDate);

                                        //login admin again
                                            auth.signOut();
                                            auth.updateCurrentUser(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    employeeDao.add(empId, emp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(NewEmpActivity.this, "Employee added successfully!", Toast.LENGTH_SHORT).show();
                                                            clearInputs();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(NewEmpActivity.this, "Could not add employee: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(NewEmpActivity.this, "Something went wrong!: " + task.getException(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    } else {
                                        Toast.makeText(NewEmpActivity.this, "Could not add employee: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void clearInputs() {
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        password.setText("");
        mission.setText("");
        phone.setText("");
        startDate.setText("");
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
        if (username.getText().toString().trim().isEmpty()) {
            username.setError("Required Field");
            username.requestFocus();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            password.setError("Required Field");
            password.requestFocus();
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