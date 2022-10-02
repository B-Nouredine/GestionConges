package com.ensaf.nour.gestion_conges.employee.attachments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.messages.AdapterItemOnClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AttachmentsActivity extends AppCompatActivity implements AdapterItemOnClickListener {

    RecyclerView recyclerView;
    final List<AttachmentUnit> attachmentUnits = new ArrayList<>();
    AttachmentsAdapter attachmentsAdapter;
    ProgressBar progressBar;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachments);

        recyclerView = findViewById(R.id.attachments_RecyclerView);
        progressBar = findViewById(R.id.attachments_progress_bar);
        progressBar.setVisibility(View.VISIBLE);


        firebaseFirestore.collection("Attachment").whereEqualTo("userID", auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                attachmentUnits.clear();
                if(value != null)
                {
                    for(QueryDocumentSnapshot snapshot: value)
                    {
                        if(snapshot.exists())
                        {
                            Uri uri = Uri.parse( (String) snapshot.getData().get("uri"));
                            attachmentUnits.add(new AttachmentUnit((String) snapshot.getData().get("name"), (long) snapshot.getData().get("dateUpload"), uri, false));
                        }
                    }

                    attachmentsAdapter = new AttachmentsAdapter(AttachmentsActivity.this, attachmentUnits, AttachmentsActivity.this);
                    recyclerView.setAdapter(attachmentsAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(AttachmentsActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVerticalScrollbarPosition(attachmentUnits.size() - 1);

        findViewById(R.id.attachments_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        AttachmentUnit attachmentUnit = attachmentUnits.get(position);
        attachmentUnit.setShowPreview(!attachmentUnit.isShowPreview());
        attachmentsAdapter = new AttachmentsAdapter(AttachmentsActivity.this, attachmentUnits, AttachmentsActivity.this);
        recyclerView.setAdapter(attachmentsAdapter);
    }
}