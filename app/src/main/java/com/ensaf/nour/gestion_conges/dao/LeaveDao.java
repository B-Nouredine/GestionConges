package com.ensaf.nour.gestion_conges.dao;

import androidx.annotation.NonNull;

import com.ensaf.nour.gestion_conges.model.Leave;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LeaveDao {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<DocumentReference> add(Leave leave)
    {
        return db.collection("Leave").add(leave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failure
            }
        });
    }

    public Task<Void> delete(String key)
    {
        return db.collection("Leave").document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failure
            }
        });
    }


}
