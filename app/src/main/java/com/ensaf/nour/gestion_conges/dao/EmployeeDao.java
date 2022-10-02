package com.ensaf.nour.gestion_conges.dao;

import com.ensaf.nour.gestion_conges.model.Employee;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class EmployeeDao {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Void> add(String key, Employee emp)
    {
        return db.collection("Employee").document(key).set(emp);
    }

    public Task<DocumentReference> add(Employee emp)
    {
        return db.collection("Employee").add(emp);
    }

    public Task<Void> remove(String key)
    {
        return db.collection("Employee").document(key).delete();
    }

    public Task<DocumentSnapshot> get(String key)
    {
        return db.collection("Employee").document(key).get();
    }

    public Task<QuerySnapshot> getAll()
    {
        return db.collection("Employee").get();
    }

    public Task<Void> update(String key, Map<String, Object> emp)
    {
        return db.collection("Employee").document(key).update(emp);
    }
}
