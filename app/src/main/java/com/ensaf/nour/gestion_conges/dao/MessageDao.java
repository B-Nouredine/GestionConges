package com.ensaf.nour.gestion_conges.dao;

import androidx.annotation.NonNull;

import com.ensaf.nour.gestion_conges.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<DocumentReference> add(Message m){
        return db.collection("Message").add(m);
    }

    public Task<Void> remove(String key){
        return db.collection("Message").document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public  Task<QuerySnapshot> getAll(){
        return db.collection("Message").get();
    }
}
