package com.eynav.wayspeack.ui.main;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eynav.wayspeack.CameraActivity;
import com.eynav.wayspeack.HomePatient;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PerformingExercise extends Fragment {
//    private CameraManager cameraManager;
//    private String cameraId;
//    private CameraDevice cameraDevice;
//    private CaptureRequest.Builder previewRequestBuilder;
//    private CameraCaptureSession cameraCaptureSession;
//    private SurfaceTexture surfaceTexture;
//    private Size previewSize;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    Patient patient;
    Exercise exercise;
    TypeExercise info;
    VideoView vvPatientExercise;
    TextView idTVHeading;
//    TextureView textureView;
    Context context;
    private Button btnRecord, btnSend;
//    private Button btnStop;
    Uri uriVideo;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


//
//    private MediaRecorder mediaRecorder;
//    private boolean isRecording = false;


    public PerformingExercise(Patient patient, Exercise exercise, TypeExercise info, Context context) {
        this.patient = patient;
        this.exercise = exercise;
        this.info = info;
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.performing_exercise, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
//        textureView = root.findViewById(R.id.textureView);
        btnRecord = root.findViewById(R.id.btnRecord);
        vvPatientExercise =  root.findViewById(R.id.vvPatientExercise);
        idTVHeading =  root.findViewById(R.id.idTVHeading);
        idTVHeading.setText(info.getType());
        btnSend =  root.findViewById(R.id.btnSend);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissions()) {
                        startRecording();
                    } else {
                        requestPermissions();
                    }

                //                if (isRecording) {
//                    stopRecording();
//                } else {
//                    if (checkPermissions()) {
//                        startRecording();
//                    } else {
//                        requestPermissions();
//                    }
//                }
            }
        });
        btnSend.setOnClickListener(l ->{
            saveVideoToFirebase(this.info,uriVideo);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("הסרטון נשלח לניתוח, במידה וההבעה בוצעה בהצלחה התעדכן כמות הפעמיים שבוצע התרגיל הזה בדף הבית")
                    .setPositiveButton("בסדר", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME!
                        }
                    });

            builder.show();

        });
//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopRecording();
//            }
//        });
    }

    private boolean checkPermissions() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                startRecording();
            } else {
                Toast.makeText(context, "Permissions not granted.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void startRecording() {
//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//
//        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//        mediaRecorder.setVideoFrameRate(profile.videoFrameRate);
//        mediaRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);
//
//        String videoFilePath = getOutputMediaFilePath();
//        mediaRecorder.setOutputFile(videoFilePath);
//
//        Surface surface = new Surface(textureView.getSurfaceTexture());
//        mediaRecorder.setPreviewDisplay(surface);
//
//        try {
//            mediaRecorder.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Failed to prepare MediaRecorder.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        mediaRecorder.start();
//        isRecording = true;
//
//        Toast.makeText(context, "Recording started.", Toast.LENGTH_SHORT).show();
//        btnRecord.setText("Stop Recording");
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);
        startActivityForResult(intent,1);
    }
    private String getOutputMediaFilePath() {
        String appName = getResources().getString(R.string.app_name);
        File mediaDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), appName);
        if (!mediaDir.exists()) {
            mediaDir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return mediaDir.getPath() + File.separator + "VIDEO_" + timeStamp + ".mp4";
    }

    private void stopRecording() {
        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;

            Toast.makeText(context, "Recording stopped.", Toast.LENGTH_SHORT).show();
            btnRecord.setText("Start Recording");

            // Save the recorded video to Firebase
//            saveVideoToFirebase(data.getData());
        }
    }

    private void saveVideoToFirebase(TypeExercise info, Uri data) {
        String dateAdd = new SimpleDateFormat("HH:mm_dd/MM/yy ", Locale.getDefault()).format(new Date());
        String formattedDate = dateAdd.replace(":", "").replace("/", "");

        FirebaseStorage storage = FirebaseStorage.getInstance();
//        patient
        StorageReference storageRef = storage.getReference().child("videos").child(patient.getIdentificationNumber()).child(this.info.getType()).child(formattedDate);
        StorageReference videoRef = storageRef.child("video.mp4");
        UploadTask uploadTask = videoRef.putFile(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // Handle successful upload
                videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {

                        String videoDownloadUrl = downloadUrl.toString();
//                        TODO send to python videoDownloadUrl
                        addExercisesTypeToFirebase(info, videoDownloadUrl);
                        System.out.println();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to retrieve download URL
                    }
                });
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                // Save the download URL or perform any other operation
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle upload failure
            }
        });
//
    }

    private void addExercisesTypeToFirebase(TypeExercise typeExercise, String data) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> Exercise = new HashMap<>();
            String dateAdd = new SimpleDateFormat("HH:mm_dd/MM/yy ", Locale.getDefault()).format(new Date());

            Exercise.put("date", dateAdd);
            Exercise.put("Image", "");
            Exercise.put("success", false);
            Exercise.put("progress", false);
//            Experience
//                    date
//            Image
//                    success
//            progress
            String dateStartF = exercise.getDateStart().replace("/", "");
            String formattedDate = dateAdd.replace(":", "").replace("/", "");

            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                    .document(dateStartF).collection("Type").document(typeExercise.getType()+typeExercise.getCount()).collection("Experience").document(formattedDate)
                    .set(Exercise)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updateCountDo();

                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Error adding document");
                        }
                    });




    }

    private void updateCountDo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String dateStartF = exercise.getDateStart().replace("/", "");

        db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                .document(dateStartF).collection("Type").document(info.getType()+info.getCount())
                .update("countDo", String.valueOf((Integer.parseInt( info.getCountDo()))+1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent clinicianOnePatient= new Intent(getContext(), HomePatient.class);

                clinicianOnePatient.putExtra("Patient", patient);

                startActivity(clinicianOnePatient);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK && requestCode == 1){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    VideoView videoView = new VideoView(getContext());
//                    videoView.setVideoURI(data.getData());
//
//                    videoView.start();
//                    builder.setView(videoView).show();

//                    Uri uri = Uri.parse(String.valueOf(data.getData()));
                    uriVideo = data.getData();
                    // sets the resource from the
                    // videoUrl to the videoView
                    vvPatientExercise.setVideoURI(uriVideo);

                    // creating object of
                    // media controller class
                    MediaController mediaController = new MediaController(getContext());

                    // sets the anchor view
                    // anchor view for the videoView
                    mediaController.setAnchorView(vvPatientExercise);

                    // sets the media player to the videoView
                    mediaController.setMediaPlayer(vvPatientExercise);

                    // sets the media controller to the videoView
                    vvPatientExercise.setMediaController(mediaController);

                    // starts the video
                    vvPatientExercise.start();
                    btnSend.setVisibility(View.VISIBLE);
//                    saveVideoToFirebase(this.info,uriVideo);

                }

    }
//    private void startRecording() {
//        // Initialize the MediaRecorder
//        mediaRecorder = new MediaRecorder();
//        // Configure the MediaRecorder for video recording
//        // Set the output file path and format
//        mediaRecorder.setOutputFile(getOutputFilePath());
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        // Set video and audio encoding parameters
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        // Set the preview surface
//        mediaRecorder.setPreviewDisplay(new Surface(surfaceTexture));
//
//        try {
//            // Prepare the MediaRecorder for recording
//            mediaRecorder.prepare();
//            // Start recording
//            mediaRecorder.start();
//            isRecording = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void stopRecording() {
//        // Stop recording
//        mediaRecorder.stop();
//        mediaRecorder.reset();
//        mediaRecorder.release();
//        mediaRecorder = null;
//        isRecording = false;
//
//        // Upload the video file to Firebase Storage
//        uploadVideoToFirebase();
//    }
//
//    private void uploadVideoToFirebase() {
//        // Get a reference to the Firebase Storage
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//        // Create a unique filename for the video
//        String filename = "video_" + System.currentTimeMillis() + ".mp4";
//        // Create a reference to the video file in Firebase Storage
//        StorageReference videoRef = storageRef.child(filename);
//
//        // Get the file URI of the recorded video
//        Uri videoUri = Uri.fromFile(new File(getOutputFilePath()));
//
//        // Upload the video file to Firebase Storage
//        videoRef.putFile(videoUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Video upload successful
//                        // You can get the download URL of the uploaded video using taskSnapshot.getDownloadUrl()
//                        // Save the download URL in your Firebase Database or perform any other desired actions
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Video upload failed
//                        // Handle the error
//                    }
//                });
//    }
//    private String getOutputFilePath() {
//        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File outputDir = new File(basePath + "/YourApp/videos");
//        outputDir.mkdirs();
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        return outputDir.getAbsolutePath() + "/video_" + timestamp + ".mp4";
//    }

}
