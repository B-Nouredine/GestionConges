package com.ensaf.nour.gestion_conges.employee.empHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ensaf.nour.gestion_conges.MainActivity;
import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EmpHomeActivity extends AppCompatActivity {

    DrawerLayout drawer;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView username;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_home);

        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);

        username = navigationView.getHeaderView(0).findViewById(R.id.username_menu);
        username.setText(auth.getCurrentUser().getEmail());

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavController navController = Navigation.findNavController(EmpHomeActivity.this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

}