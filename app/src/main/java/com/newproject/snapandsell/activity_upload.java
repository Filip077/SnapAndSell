package com.newproject.snapandsell;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.newproject.snapandsell.Model.Product;
import com.squareup.picasso.Picasso;

public class activity_upload extends AppCompatActivity {
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Button mOpen_galery , mUpload;
    private EditText mPrice , mDescription;
    private ImageView mImage;
    private StorageTask mUploadTask;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mOpen_galery = findViewById(R.id.button_select_photo);
        mUpload = findViewById(R.id.button_upload_image);
        mDescription = findViewById(R.id.description_editText);
        mPrice = findViewById(R.id.price_editText);
        mImage = findViewById(R.id.product_image);
        toolbar =findViewById(R.id.toolbar);

toolbar.setNavigationOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity_upload.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
});
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mOpen_galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPrice.getText().toString().length() == 0){
                    mPrice.setError("Enter product's price.");
                }
                if(mDescription.getText().toString().length() == 0){
                    mDescription.setError("Enter product's price.");
                }
            else {
                    uploadFile();
                }
            }
        });


    }




    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).fit().centerInside().rotate(90).into(mImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        final ProgressDialog progressDialog = new ProgressDialog(activity_upload.this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Product product = new Product(taskSnapshot.getDownloadUrl().toString(),
                            mDescription.getText().toString(),mPrice.getText().toString().trim());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(product);



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity_upload.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            progressDialog.dismiss();
            Toast.makeText(activity_upload.this, "Upload successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity_upload.this,MainActivity.class);
            finish();
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
