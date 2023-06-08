package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CameraActivity extends AppCompatActivity {
    Button btnCamera, btnGallery;
    ImageView imStar;
    static Bitmap bitmap;
    Patient patient;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    long timestamp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        btnCamera = findViewById(R.id.btnCamera);
        patient = (Patient) getIntent().getParcelableExtra("Patient");

        imStar = findViewById(R.id.imStar);
        btnGallery = findViewById(R.id.btnGallery);
        readImageFromFirebase(true);
        btnGallery.setOnClickListener(l ->{
            if (timestamp > 0){
//                long dateBeforeInMs = dateBefore.getTime();

//                LocalDate dateAdd = LocalDate.now();
//
////                String dateAdd = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
//                String[] dateStartSplit = dateAdd.split("/", 5);
//
////                Date dateAfter = new Date(Integer.parseInt(dateStartSplit[2]), Integer.parseInt(dateStartSplit[1]) - 1, Integer.parseInt(dateStartSplit[0]));
//
//                long timeDiff = Math.abs(dateAfter.getTime() - new Date(timestamp).getTime());

//                long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
//                daysDiff = daysDiff +1;
//                System.out.println(" The number of days between dates: " + daysDiff);
                LocalDate uploadDate = LocalDate.ofEpochDay(timestamp / (24 * 60 * 60 * 1000));

// Get the current date
                LocalDate currentDate = LocalDate.now();

// Calculate the number of days between uploadDate and currentDate
                long daysPassed = ChronoUnit.DAYS.between(uploadDate, currentDate);
                System.out.println("daysPassed "+daysPassed);
                if (daysPassed < 30){
                    Intent clinicianOnePatient= new Intent(CameraActivity.this, HomePatient.class);

                    clinicianOnePatient.putExtra("Patient", patient);

                    startActivity(clinicianOnePatient);
                }else {
                    Toast.makeText(this, "צריך לעדכן תמונה", Toast.LENGTH_SHORT).show();


                }
            }else {
                Toast.makeText(this, "צריך לעדכן תמונה", Toast.LENGTH_SHORT).show();

            }


        });

        btnCamera.setOnClickListener(l ->{
            // Check if the camera permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, 100);
            }



        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, start the camera
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, 100);
            } else {
                // Camera permission denied, handle accordingly (e.g., show a message or disable camera functionality)
            }
        }
    }
    private void saveImageInFirebase(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference riversRef = storage.getReference().child("images/"+patient.getIdentificationNumber()+"/comparison/image.jpg");
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("uploadDate", String.valueOf(System.currentTimeMillis()))
                .build();
        timestamp = System.currentTimeMillis();

// Upload the byte array to Firebase Storage with metadata
        UploadTask uploadTask = riversRef.putBytes(data, metadata);
//        UploadTask uploadTask = riversRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        });
        
    }

    // You can perform any additional actions here, such as
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            System.out.println(requestCode);

            if (requestCode == 1000){
//                gallery
                imStar.setImageURI(data.getData());
            }

            if (requestCode == 100){
//                camera
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imStar.setImageBitmap(bitmap);
                saveImageInFirebase(bitmap);
            }
        }

    }

    private void readImageFromFirebase(boolean b) {

// Create a reference to the image file in Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/"+patient.getIdentificationNumber()+"/comparison/image.jpg");

// Download the image file as a byte array from Firebase Storage
        final long ONE_MEGABYTE = 1024 * 1024;

        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            // Convert the byte array to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            // Retrieve the metadata for the image
            storageRef.getMetadata().addOnSuccessListener(storageMetadata -> {
                // Retrieve the upload date from the custom metadata
                String uploadDate = storageMetadata.getCustomMetadata("uploadDate");
                timestamp = Long.parseLong(uploadDate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                String formattedDate = dateFormat.format(new Date(timestamp));
                // Use the upload date as desired
                // You can display it, store it, or perform any other actions with it
                Log.d("Upload Date", "Image upload date: " + formattedDate);
            }).addOnFailureListener(exception -> {
                // Handle any errors that occurred while retrieving metadata
                // You can display an error message or handle the exception as per your requirement
            });

            // Use the Bitmap as desired, such as displaying it in an ImageView
            imStar.setImageBitmap(bitmap);
        }).addOnFailureListener(exception -> {
            Toast.makeText(this, "צריך לעדכן תמונה", Toast.LENGTH_SHORT).show();

            // Handle any errors that occurred during the download
            // You can display an error message or handle the exception as per your requirement
        });

    }
}