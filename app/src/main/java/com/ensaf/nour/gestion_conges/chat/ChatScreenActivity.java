package com.ensaf.nour.gestion_conges.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.MessageDao;
import com.ensaf.nour.gestion_conges.model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatScreenActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    MessageDao messageDao;
    private final List<ChatUnit> chatUnits = new ArrayList<>();
    private ChatAdapter chatAdapter;

    private RecyclerView chatRecyclerView;
    private EditText messageTitle;
    private EditText messageContent;
    private ImageView sendBtn;
    private TextView empNameTV;
    private ImageView empProfilePic;

    private String empID = "";
    private String empName = "";
    private String empProfilePicUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageTitle = findViewById(R.id.message_title);
        messageContent = findViewById(R.id.message_content);
        sendBtn = findViewById(R.id.btn_send_message);

        empNameTV = findViewById(R.id.emp_name);
        empProfilePic = findViewById(R.id.emp_profile_pic);

        Bundle empData = getIntent().getExtras();
        if (empData != null) {
            empID = empData.getString("empID");
            empName = empData.getString("empName");
            empProfilePicUrl = empData.getString("empProfilePic");
        }
        empNameTV.setText(empName);
        if (empProfilePicUrl != null && empProfilePicUrl != "")
        {
            Picasso.get().load(empProfilePicUrl).into(empProfilePic);
        }

        ArrayList<Map<String, Object>> messagesRecords = new ArrayList<>();
        firebaseFirestore.collection("Message").orderBy("dateMessage").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ChatScreenActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    chatUnits.clear();
                    messagesRecords.clear();
                    for (QueryDocumentSnapshot document : value) {
                        if (document.exists()) {
                            messagesRecords.add(document.getData());
                        }
                    }
                    String currentUserID = auth.getCurrentUser().getUid();
                    for(int i=0; i<messagesRecords.size(); i++)
                    {
                        String recID = (String) messagesRecords.get(i).get("receiverID");
                        String senID = (String) messagesRecords.get(i).get("senderID");
                        if (recID.equals(empID) && senID.equals(currentUserID)  ||  recID.equals(currentUserID) && senID.equals(empID))
                        {
                            chatUnits.add(new ChatUnit(senID, (String) messagesRecords.get(i).get("title"), (String) messagesRecords.get(i).get("message"), (long) messagesRecords.get(i).get("dateMessage")));
                        }
                    }
                    chatAdapter = new ChatAdapter(chatUnits, ChatScreenActivity.this);
                    chatRecyclerView.setAdapter(chatAdapter);
                }
            }
        });

        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(ChatScreenActivity.this));
        chatRecyclerView.setNestedScrollingEnabled(false);
        chatRecyclerView.setVerticalScrollbarPosition(chatUnits.size() - 1);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageTitle.getText().toString().isEmpty() || messageContent.getText().toString().isEmpty()) {
                    Toast.makeText(ChatScreenActivity.this, "Title and Message fields are required!", Toast.LENGTH_SHORT).show();
                    messageTitle.setError("");
                    messageContent.setError("");
                    return;
                }

                Message message = new Message();
                message.setSenderID(auth.getCurrentUser().getUid());
                message.setReceiverID(empID);
                message.setTitle(messageTitle.getText().toString().trim());
                message.setMessage(messageContent.getText().toString().trim());
                message.setDateMessage(System.currentTimeMillis());

                messageDao = new MessageDao();
                messageDao.add(message)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // refresh messages
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChatScreenActivity.this, "Something went wrong! message not sent", Toast.LENGTH_SHORT).show();
                            }
                        });

                clearInputs();
            }
        });


        findViewById(R.id.chat_back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void clearInputs() {
        this.messageTitle.setText("");
        this.messageContent.setText("");
    }
}