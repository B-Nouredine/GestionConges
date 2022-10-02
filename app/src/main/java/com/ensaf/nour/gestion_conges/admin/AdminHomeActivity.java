package com.ensaf.nour.gestion_conges.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ensaf.nour.gestion_conges.MainActivity;
import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.admin.employees.EmployeesActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


    }

    public void redirectToProfile(View v)
    {
        startActivity(new Intent(AdminHomeActivity.this, ProfileActivity.class));
    }

    public void redirectToEmployees(View v)
    {
        startActivity(new Intent(AdminHomeActivity.this, EmployeesActivity.class));
    }

    public void redirectToMessages(View v)
    {
        startActivity(new Intent(AdminHomeActivity.this, MessagesActivity.class));
    }

    public void redirectToAttachments(View v)
    {
        startActivity(new Intent(AdminHomeActivity.this, AttachmentsActivity.class));
    }

    public void redirectToLeaveRequests(View v)
    {

    }

    public void redirectToSettings(View v)
    {

    }

    public void logout(View v)
    {
        auth.signOut();
        finish();
        startActivity(new Intent(AdminHomeActivity.this, MainActivity.class));
    }

}