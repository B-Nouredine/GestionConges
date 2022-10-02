package com.ensaf.nour.gestion_conges.employee.empHome.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ensaf.nour.gestion_conges.R;
import com.ensaf.nour.gestion_conges.dao.AttachmentDao;
import com.ensaf.nour.gestion_conges.model.Attachment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class AttachmentFragment extends Fragment {


    private Button chooseFileBtn;
    private Button uploadFileBtn;
    private EditText fileName;
    private ProgressBar progressBar;
    private ImageView imageView;

    ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageUri = null;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads/");
    AttachmentDao attachmentDao;

    public AttachmentFragment() {
        // Required empty public constructor
    }

    public static AttachmentFragment newInstance(String param1, String param2) {
        AttachmentFragment fragment = new AttachmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attachment, container, false);

        chooseFileBtn = view.findViewById(R.id.btn_choose_file);
        uploadFileBtn = view.findViewById(R.id.btn_upload_file);
        fileName = view.findViewById(R.id.file_name_edit);
        progressBar = view.findViewById(R.id.upload_file_progress_bar);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        imageView = view.findViewById(R.id.file_image_view);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            imageUri = result.getData().getData();
                            if (imageUri != null) {
                                Picasso.get().load(imageUri).into(imageView);
                            } else {
                                Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        chooseFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mimeTypes = "image/*|application/pdf";

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(mimeTypes);
                activityResultLauncher.launch(intent);
            }
        });

        uploadFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = auth.getCurrentUser().getUid();
                StorageReference filerReference;
                long dateUpload;

                if (imageUri == null || imageView.getDrawable().getConstantState().equals(getContext().getDrawable(R.drawable.done).getConstantState())) {
                    Toast.makeText(getContext(), "No file selected!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (fileName.getText().toString().isEmpty()) {
                    fileName.setError("Please provide a file name");
                    return;
                }

                dateUpload = System.currentTimeMillis();
                filerReference = storageReference.child(String.valueOf(dateUpload));
                filerReference.putFile(imageUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressBar.setProgress((int) progress);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(0);
                            }
                        }, 2000);

                        filerReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    String firebaseStorageUri = task.getResult().toString();
                                    Attachment file = new Attachment(fileName.getText().toString().trim(), firebaseStorageUri, userID, dateUpload);
                                    attachmentDao = new AttachmentDao();
                                    attachmentDao.add(file).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "File uploaded successfully!", Toast.LENGTH_SHORT).show();
                                                imageView.setImageResource(R.drawable.done);
                                                fileName.setText("");
                                            } else {
                                                Toast.makeText(getContext(), "Could not upload file" + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), "Could not upload file" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        return view;
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}