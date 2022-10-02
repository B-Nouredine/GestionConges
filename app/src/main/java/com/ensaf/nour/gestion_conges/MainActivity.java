package com.ensaf.nour.gestion_conges;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.ensaf.nour.gestion_conges.admin.AdminHomeActivity;
import com.ensaf.nour.gestion_conges.employee.empHome.EmpHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    EmployeeDao employeeDao;
    EditText usernameEdit;
    EditText passwordEdit;
    Button signIn;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_starter_layout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    checkMissionAndRedirect(user.getUid());
                } else {
                    startLoginActivity();
                }
            }
        }, 1000);
    }

    void startLoginActivity() {
        setContentView(R.layout.activity_main);

        usernameEdit = findViewById(R.id.inputUsername);
        passwordEdit = findViewById(R.id.inputPassword);
        signIn = findViewById(R.id.signin_btn);
        progressBar = findViewById(R.id.signin_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String username = usernameEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString();

                if (password.isEmpty()) {
                    passwordEdit.setError("Please enter the password");
                    passwordEdit.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                }

                if (username.isEmpty()) {
                    usernameEdit.setError("Please provide a username");
                    usernameEdit.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                }


                if (!username.isEmpty() && !password.isEmpty()) {
                    auth.signInWithEmailAndPassword(username, password)
                            .addOnSuccessListener(task -> {
                                FirebaseUser currentUser = task.getUser();
                                if (currentUser != null) {
                                    checkMissionAndRedirect(currentUser.getUid());
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                    auth.signOut();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }).addOnFailureListener(e -> {
                                Toast.makeText(MainActivity.this, "Something went wrong! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                progressBar.setVisibility(View.INVISIBLE);
                            });
                }

            }
        });
    }

    void startEmpHomeActivity() {
        startActivity(new Intent(MainActivity.this, EmpHomeActivity.class));
        finish();
    }

    void startAdminHomeActivity() {
        startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
        finish();
    }

    void checkMissionAndRedirect(String uid)
    {
        employeeDao = new EmployeeDao();
        employeeDao.get(uid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    String mission = (String) task.getResult().get("mission");
                    Toast.makeText(MainActivity.this, "Signed In successfully!", Toast.LENGTH_SHORT).show();
                    if ("admin".equals(mission)) {
                        startAdminHomeActivity();
                    } else {
                        startEmpHomeActivity();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No employee matches such credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}