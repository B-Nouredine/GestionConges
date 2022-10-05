package com.ensaf.nour.gestion_conges.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EmployeeDao employeeDao = new EmployeeDao();
    
    ImageView profilePic;
    TextView firstName;
    TextView lastName;
    TextView username;
    TextView password;
    TextView phone;
    Button saveBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        profilePic = findViewById(R.id.admin_profile_pic);
        firstName = findViewById(R.id.admin_profile_first_name);
        lastName = findViewById(R.id.admin_profile_last_name);
        username = findViewById(R.id.admin_profile_username);
        password = findViewById(R.id.admin_profile_password);
        phone = findViewById(R.id.admin_profile_phone);
        saveBtn = findViewById(R.id.admin_profile_save_btn);
        progressBar = findViewById(R.id.admin_emp_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        
        employeeDao.get(auth.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot admin) {
                if(admin.exists())
                {
                    firstName.setText((String) admin.get("firstName"));
                    lastName.setText((String) admin.get("lastName"));
                    username.setText((String) admin.get("username"));
                    password.setText((String) admin.get("password"));
                    phone.setText((String) admin.get("phone"));

                    String pic = (String) admin.get("profilePic");
                    if(pic != null && !pic.equals(""))
                    {
                        Picasso.get().load(pic).into(profilePic);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFieldsAreSet())
                {
                    updateAdminInfo();
                    finish();
                }
            }
        });
        
    }

    private boolean allFieldsAreSet() {
        if (firstName.getText().toString().trim().isEmpty()) {
            firstName.setError("Field cannot be empty");
            firstName.requestFocus();
            return false;
        }
        if (lastName.getText().toString().trim().isEmpty()) {
            lastName.setError("Field cannot be empty");
            lastName.requestFocus();
            return false;
        }
        if (username.getText().toString().trim().isEmpty()) {
            username.setError("Field cannot be empty");
            username.requestFocus();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            password.setError("Field cannot be empty");
            password.requestFocus();
            return false;
        }
        if (phone.getText().toString().trim().isEmpty()) {
            phone.setError("Field cannot be empty");
            phone.requestFocus();
            return false;
        }

        return true;
    }

    private void updateAdminInfo()
    {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> admin = new HashMap<>();
        admin.put("firstName", firstName.getText().toString().trim());
        admin.put("lastName", lastName.getText().toString().trim());
        admin.put("username", username.getText().toString().trim());
        admin.put("password", password.getText().toString());
        admin.put("phone", phone.getText().toString().trim());

        assert auth.getCurrentUser() != null;
        auth.getCurrentUser().updateEmail(username.getText().toString().trim());
        auth.getCurrentUser().updatePassword(password.getText().toString().trim());

        employeeDao.update(auth.getCurrentUser().getUid(), admin).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProfileActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Could not update! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}