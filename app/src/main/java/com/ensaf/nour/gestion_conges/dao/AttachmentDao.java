package com.ensaf.nour.gestion_conges.dao;

import androidx.annotation.NonNull;

import com.ensaf.nour.gestion_conges.model.Attachment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AttachmentDao {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<DocumentReference> add(Attachment attachment){
        return db.collection("Attachment").add(attachment);
    }

    public Task<Void> remove(String key){
        return db.collection("Attachment").document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        return db.collection("Attachment").get();
    }
}
