package com.eynav.wayspeack.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExerciseInstructions  extends Fragment {
    Patient patient;
    Exercise exercise;
    TypeExercise info;
    TextView tvExerciseInstruction, tvExerciseInstructionInfo;
    Context context;
    VideoView vvExerciseInstruction;
    public ExerciseInstructions(Patient patient, Exercise exercise, TypeExercise info, Context context) {
        this.patient = patient;
        this.exercise = exercise;
        this.info = info;
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exercise_instructions, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        tvExerciseInstruction = root.findViewById(R.id.tvExerciseInstruction);
        tvExerciseInstructionInfo = root.findViewById(R.id.tvExerciseInstructionInfo);
        vvExerciseInstruction = root.findViewById(R.id.vvExerciseInstruction);
        tvExerciseInstruction.setText(info.getType());




        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(vvExerciseInstruction);
        vvExerciseInstruction.setMediaController(mediaController);

        String videoPath ="https://firebasestorage.googleapis.com/v0/b/wayspeack.appspot.com/o/explanation%2F"+info.getType()+".mp4?alt=media";
//        "https://firebasestorage.googleapis.com/v0/b/wayspeack.appspot.com/o/explanation%2Fmouth_open.mp4?alt=media&token=783e8d4c-7486-42d5-9eac-90e2526320b9";
        System.out.println(videoPath);
        Uri videoUri = Uri.parse(videoPath);

        vvExerciseInstruction.setVideoURI(videoUri);
//        vvExerciseInstruction.start();
        vvExerciseInstruction.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            vvExerciseInstruction.start();
        });





        // Replace "explanation.json" with the actual path of your JSON file in Firebase Storage
//        String jsonFilePath = "https://firebasestorage.googleapis.com/v0/b/wayspeack.appspot.com/o/explanation%2Fexplanation.json?alt=media&token=db474fec-7528-4afe-8876-20dc84067e19";
        new ReadJsonTask().execute("https://firebasestorage.googleapis.com/v0/b/wayspeack.appspot.com/o/explanation%2Fexplanation.json?alt=media&token=db474fec-7528-4afe-8876-20dc84067e19");
//
//        // Get the reference to the Firebase Storage file
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child(jsonFilePath);
//
//        // Fetch the file content as a stream
//        StreamDownloadTask task = storageRef.getStream();
//        task.addOnCompleteListener(new OnCompleteListener<StreamDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<StreamDownloadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Read the content of the file from the stream
//                    InputStream inputStream = task.getResult().getStream();
//
//                    try {
//                        String jsonString = readStreamToString(inputStream);
//
//                        // Parse the JSON and extract the "smile" value
//                        JSONObject jsonObject = new JSONObject(jsonString);
//                        String smileValue = jsonObject.getString(info.getType());
//
//                        // Use the "smile" value as needed
//                        Log.d("SMILE_VALUE", smileValue);
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    // Handle the download failure
//                    Log.e("TAG", "Error downloading file: " + task.getException());
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Handle the download failure
//                Log.e("TAG", "Error downloading file: " + e.getMessage());
//            }
//        });

    }

        private class ReadJsonTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String urlString = params[0];

                StringBuilder result = new StringBuilder();

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            result.append(line);
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return result.toString();
            }

            @Override
            protected void onPostExecute(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);

                    String smileValue = jsonObject.getString(info.getType());
                    tvExerciseInstructionInfo.setText(smileValue);

                    // Use the "smileValue" as needed
                    Log.d("JSON", "Smile Value: " + smileValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }




}
