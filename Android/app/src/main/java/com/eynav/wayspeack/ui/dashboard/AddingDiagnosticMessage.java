package com.eynav.wayspeack.ui.dashboard;

import static com.eynav.wayspeack.ui.dashboard.DashboardFragment.CHATTY_MSG_LENGTH_KEY;
import static com.eynav.wayspeack.ui.dashboard.DashboardFragment.DEFAULT_MSG_LENGTH_LIMIT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eynav.wayspeack.BuildConfig;
import com.eynav.wayspeack.Clinician;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddingDiagnosticMessage extends Fragment {
    Patient patient;
    private EditText mMessageEditText, messageEditTextTitle;
    private Button mSendButton;
    private DatabaseReference mMessageDbReference;
    private FirebaseDatabase mFireDb;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    Clinician clinician;
    private String mUsername;
    boolean editViewEdit;
    FriendlyMessage message;
    TextView tvTitleMessage;
    public AddingDiagnosticMessage(Patient patient, Clinician clinician, boolean b, FriendlyMessage message) {
        this.patient = patient;
        this.clinician = clinician;
        this.editViewEdit = b;
        this.message = message;
    }
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
//        editText.setKeyListener(null);
//        editText.setBackgroundColor(Color.TRANSPARENT);
    }
    private void ableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
//        editText.setKeyListener(null);
//        editText.setBackgroundColor(Color.TRANSPARENT);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        patient = (Patient) getActivity().getIntent().getParcelableExtra("Patient");
//        clinician = (Clinician) getActivity().getIntent().getParcelableExtra("Clinician");

        return inflater.inflate(R.layout.adding_diagnostic_message, container, false);


    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        mMessageEditText = root.findViewById(R.id.messageEditText);
        mSendButton = root.findViewById(R.id.sendButton);
        messageEditTextTitle = root.findViewById(R.id.messageEditTextTitle);
        tvTitleMessage = root.findViewById(R.id.tvTitleMessage);
        mFireDb = FirebaseDatabase.getInstance();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mUsername = clinician.getName();
        if (!editViewEdit){
            disableEditText(mMessageEditText);
            disableEditText(messageEditTextTitle);
            mMessageEditText.setText(message.getFullText());
            messageEditTextTitle.setText(message.getText());
            mSendButton.setText("חזרה");
            tvTitleMessage.setText( message.getDateAdd()+"האבחון ");

        }
        if (editViewEdit){
            ableEditText(mMessageEditText);
            ableEditText(messageEditTextTitle);
        }
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings
                .Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);

        Map<String, Object> defConfig = new HashMap<>();
        defConfig.put(CHATTY_MSG_LENGTH_KEY, DEFAULT_MSG_LENGTH_LIMIT);
        mFirebaseRemoteConfig.setDefaults(defConfig);
        mMessageDbReference = mFireDb.getReference().child("messages").child(patient.getClinicName()).child(patient.getIdentificationNumber());

//        mMessageEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().length() > 0) {
//                    mSendButton.setEnabled(true);
//                } else {
//                    mSendButton.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                if (editViewEdit){
                    requestInternet();
                }
                Fragment myFragment = new DashboardFragment(patient, clinician);
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

            }
        });
        fetchConfig();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void requestInternet() {
        Dexter.withActivity(getActivity())
                .withPermission(
                        Manifest.permission.INTERNET
                )
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        String timeStamp = new SimpleDateFormat("HH:mm_dd/MM/yy ", Locale.getDefault()).format(new Date());
                        System.out.println("timeStamp "+timeStamp);
                        FriendlyMessage friendlyMessage = new FriendlyMessage(messageEditTextTitle.getText().toString(), mUsername,mMessageEditText.getText().toString(),timeStamp);
                        mMessageDbReference.push().setValue(friendlyMessage);
                        // Clear input box
                        mMessageEditText.setText("");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.e("Dexter", "There was an error: " + error.toString());
                    }
                })
                .onSameThread()
                .check();
    }
    private void applyLengthLimit() {
        Long chatty_message_length = mFirebaseRemoteConfig.getLong(CHATTY_MSG_LENGTH_KEY);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(chatty_message_length.intValue())});
    }

    private void fetchConfig() {
        long cacheExpiration = 3600;
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFirebaseRemoteConfig.activateFetched();
                applyLengthLimit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                applyLengthLimit();
            }
        });
    }

}
