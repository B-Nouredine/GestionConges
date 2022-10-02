package com.ensaf.nour.gestion_conges.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.EmployeeDao;
import com.ensaf.nour.gestion_conges.dao.MessageDao;
import com.ensaf.nour.gestion_conges.chat.ChatScreenActivity;
import com.ensaf.nour.gestion_conges.messages.AdapterItemOnClickListener;
import com.ensaf.nour.gestion_conges.messages.MessagesAdapter;
import com.ensaf.nour.gestion_conges.messages.MessagesUnit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MessagesActivity extends AppCompatActivity implements AdapterItemOnClickListener {

    private RecyclerView messagesRecyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private final List<MessagesUnit> messagesUnits = new ArrayList<>();
    private MessagesAdapter messagesAdapter;
    private MessageDao messageDao = new MessageDao();
    private EmployeeDao employeeDao = new EmployeeDao();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_messages);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        progressBar = findViewById(R.id.messages_progress_bar);
        searchView = findViewById(R.id.search_employees);
        searchView.clearFocus();
        findViewById(R.id.fm_empty_view).setVisibility(View.GONE);
        findViewById(R.id.admin_messages_layoutToolBar).setVisibility(View.VISIBLE);

        messagesAdapter = new MessagesAdapter(messagesUnits, MessagesActivity.this, this);
        messagesRecyclerView.setAdapter(messagesAdapter);

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(MessagesActivity.this));
        progressBar.setVisibility(View.VISIBLE);

        ArrayList<Map<String, Object>> messagesRecords = new ArrayList<>();
        messageDao.getAll().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        if(document.exists())
                            messagesRecords.add(document.getData());
                    }

                    final Set<String> addedUsers = new HashSet<>();
                    for(int i=0; i<messagesRecords.size(); i++)
                    {
                        int finalI = i;
                        String senderID = (String) messagesRecords.get(i).get("senderID");

                        employeeDao.get(senderID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                final Map<String, Object> sender;
                                if (documentSnapshot.exists())
                                    sender = documentSnapshot.getData();
                                else {
                                    sender = new HashMap<>();
                                }

                                String receiverID = (String) messagesRecords.get(finalI).get("receiverID");
                                employeeDao.get(receiverID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Map<String, Object> receiver = new HashMap<>();
                                        FirebaseUser currentUser = auth.getCurrentUser();

                                        if (documentSnapshot.exists())
                                            receiver = documentSnapshot.getData();

                                        if(Objects.equals(currentUser.getEmail(), receiver.get("username"))) {
                                            if (!addedUsers.contains((String) sender.get("username"))) {
                                                addedUsers.add((String) sender.get("username"));
                                                String profilePic = (String) sender.get("profilePic");
                                                if (profilePic == null || profilePic == "")
                                                {
                                                    profilePic = "https://firebasestorage.googleapis.com/v0/b/gestion-conges-aug2022.appspot.com/o/default_profile.png?alt=media&token=5f0e04b7-eb94-4986-895f-d20113548318";
                                                }
                                                messagesUnits.add(new MessagesUnit(senderID, sender.get("firstName") + " " + sender.get("lastName"), (String) sender.get("username"), "last message", profilePic, (String) sender.get("mission")));
                                            }
                                        }

                                        if(Objects.equals(currentUser.getEmail(), sender.get("username"))) {
                                            if ( !addedUsers.contains((String) receiver.get("username"))) {
                                                addedUsers.add((String) receiver.get("username"));
                                                messagesUnits.add(new MessagesUnit(receiverID, receiver.get("firstName") + " " + receiver.get("lastName"), (String) receiver.get("username"), "last message", (String) receiver.get("profilePic"), (String) receiver.get("mission")));
                                            }
                                        }

                                        ((MessagesAdapter) (messagesRecyclerView.getAdapter())).setMessagesUnits(messagesUnits);
                                        messagesRecyclerView.getAdapter().notifyItemChanged(messagesUnits.size()-1);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MessagesActivity.this, "Couldn't load messages, an error has occurred!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MessagesActivity.this, "Couldn't load messages, an error has occurred!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MessagesActivity.this, "Couldn't load messages, an error has occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search by username");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.trim().equals("")) {
                    ((MessagesAdapter) (messagesRecyclerView.getAdapter())).setMessagesUnits(messagesUnits);
                    messagesRecyclerView.getAdapter().notifyDataSetChanged();
                }
                else
                {
                    showEmployeesMatching(newText.trim());
                }
                return true;
            }
        });

    }

    private void showEmployeesMatching(String text)
    {
        List<MessagesUnit> newMessagesUnits = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);

        db.collection("Employee").whereNotEqualTo("username", auth.getCurrentUser().getEmail()).whereGreaterThanOrEqualTo("username", text).whereLessThanOrEqualTo("username", text + '\uf8ff').addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null)
                {
                    for(QueryDocumentSnapshot emp: value)
                    {
                        if(emp.exists())
                        {
                            String profilePic = (String) emp.get("profilePic");
                            if (profilePic == null || profilePic == "")
                            {
                                profilePic = "https://firebasestorage.googleapis.com/v0/b/gestion-conges-aug2022.appspot.com/o/default_profile.png?alt=media&token=5f0e04b7-eb94-4986-895f-d20113548318";
                            }
                            newMessagesUnits.add(new MessagesUnit(emp.getId(), emp.getData().get("firstName") + " " + emp.get("lastName"), (String) emp.getData().get("username"), (String) emp.getData().get("last message"), profilePic, (String) emp.getData().get("mission")));
                        }
                    }
                }
            }
        });

        ((MessagesAdapter) messagesRecyclerView.getAdapter()).setMessagesUnits(newMessagesUnits);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(int position) {
        MessagesUnit messagesUnit = ((MessagesAdapter) messagesRecyclerView.getAdapter()).getMessagesUnits().get(position);

        Intent intent = new Intent(MessagesActivity.this, ChatScreenActivity.class);
        intent.putExtra("empName", messagesUnit.getEmployeeName());
        intent.putExtra("empProfilePic", messagesUnit.getProfilePic());
        intent.putExtra("empID", messagesUnit.getEmployeeID());
        startActivity(intent);
    }

}