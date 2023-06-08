package com.eynav.wayspeack.ui.dashboard;

import static java.security.Security.getProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eynav.wayspeack.BuildConfig;
import com.eynav.wayspeack.Clinician;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private static final String TAG = "DashboardFragment";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static final String CHATTY_MSG_LENGTH_KEY = "chatty_message_length";

    private static final int RC_SIGN_IN = 1000;
    public static final int RC_PHOTO_PICKER = 1001;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
//    private ProgressBar mProgressBar;
//    private ImageButton mPhotoPickerButton;


    private String mUsername;
    //Firebase Variables
    private FirebaseDatabase mFireDb;
    private DatabaseReference mMessageDbReference;
    private ChildEventListener mChildEvListener;
    private FirebaseStorage mFireStorage;
//    private StorageReference mStoreReference;
    private FirebaseAuth mFirebaseAuthentication;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    Patient patient;
    Clinician clinician;
    Button btnAddMessage;
    public DashboardFragment(){

    }
    public DashboardFragment(Patient patient, Clinician clinician) {
        this.clinician = clinician;
        this.patient = patient;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        patient = (Patient) getActivity().getIntent().getParcelableExtra("Patient");
        clinician = (Clinician) getActivity().getIntent().getParcelableExtra("Clinician");
        return inflater.inflate(R.layout.fragment_dashboard, container, false);


    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        btnAddMessage = root.findViewById(R.id.btnAddMessage);
        mUsername = clinician.getName();
        //Initialise Firebase
        mFirebaseAuthentication = FirebaseAuth.getInstance();
        System.out.println("mFirebaseAuthentication "+mFirebaseAuthentication);
        mFireDb = FirebaseDatabase.getInstance();
        System.out.println("mFireDb "+mFireDb);
//        mFireStorage = FirebaseStorage.getInstance();
        System.out.println();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        System.out.println("mFirebaseRemoteConfig "+mFirebaseRemoteConfig);
        mMessageDbReference = mFireDb.getReference().child("messages").child(patient.getClinicName()).child(patient.getIdentificationNumber());
//        mStoreReference = mFireStorage.getReference().child("chatty_photos");
        attachDatabaseReadListener();
        // Initialize references to views
//        mProgressBar = root.findViewById(R.id.progressBar);
        mMessageListView = root.findViewById(R.id.messageListView);
//        mPhotoPickerButton = root.findViewById(R.id.photoPickerButton);


        // Initialize message ListView and its adapter
        final List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(getContext(), R.layout.item_message, friendlyMessages,patient, clinician);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
//        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        btnAddMessage.setOnClickListener(l ->{
            Fragment myFragment = new AddingDiagnosticMessage(patient, clinician, true, null);
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
        });
        // ImagePickerButton shows an image picker to upload a image for a message
//        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestExternalStorage();
//            }
//        });

        // Enable Send button when there's text to send

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    mUsername = clinician.getName();
//
////                    onSignedInInitialize(user.getDisplayName());
//                }
////                else {
////                    onSignOutCleanUp();
////                    startActivityForResult(
////                            AuthUI
////                                    .getInstance()
////                                    .createSignInIntentBuilder()
////                                    .setIsSmartLockEnabled(true)
////                                    .setAvailableProviders(getProviders())
////                                    .build(), RC_SIGN_IN);
////                }
//            }
//        };
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings
                .Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        System.out.println("mFirebaseRemoteConfig "+mFirebaseRemoteConfig);
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);

        Map<String, Object> defConfig = new HashMap<>();
        defConfig.put(CHATTY_MSG_LENGTH_KEY, DEFAULT_MSG_LENGTH_LIMIT);
        mFirebaseRemoteConfig.setDefaults(defConfig);
//        fetchConfig();
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(getContext(), "Signed In", Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED) {
////                finish();
//            }
//        }
//
//    }


    @Override
    public void onStart() {
        super.onStart();
//        mFirebaseAuthentication.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (mAuthStateListener != null) {
//            mFirebaseAuthentication.removeAuthStateListener(mAuthStateListener);
//        }
        mMessageAdapter.clear();
        detachDatabaseReadListener();

    }


//    private List<AuthUI.IdpConfig> getProviders() {
//        return Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build()
//        );
//    }

//    private void onSignedInInitialize(String Username) {
//        mUsername = Username;
//        attachDatabaseReadListener();
//    }
//
//    private void onSignOutCleanUp() {
//        mUsername = ANONYMOUS;
//        mMessageAdapter.clear();
//        detachDatabaseReadListener();
//
//    }

    private void detachDatabaseReadListener() {
        if (mChildEvListener != null) {
            mMessageDbReference.removeEventListener(mChildEvListener);
            mChildEvListener = null;
        }
    }

    void attachDatabaseReadListener() {
        System.out.println("attachDatabaseReadListener");
        if (mChildEvListener == null) {
            mChildEvListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                    System.out.println(friendlyMessage);
                    mMessageAdapter.add(friendlyMessage);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            System.out.println(mMessageDbReference);
            mMessageDbReference.addChildEventListener(mChildEvListener);
        }
    }






}