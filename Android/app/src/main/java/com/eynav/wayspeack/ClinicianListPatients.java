package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicianListPatients extends AppCompatActivity {
    RecyclerView rvPatient;
    FloatingActionButton fabAddPatient;
    List<Patient> Patients = new ArrayList<>();
    ClinicianListPatientsAdapter PatientsAdapter;
    String dateBirthPatient = "",dateMeeting="", hourMeeting="";
    boolean test = true;
    SharedPreferences shareType;
    SharedPreferences shareName;
    String[] type = {"נקבה", "זכר"};
    String gender = "סוג";
    String typePage ;
    Clinician nameUser ;
    Context context = this;
    Activity activity = this;
    String  hour ="0",minute ="0";
    Loading loadingdialog = new Loading(activity);
    Clinician clinician;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician_list_patients);
        shareType = this.getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");

//        shareName = this.getSharedPreferences("name", MODE_PRIVATE);
//        nameUser = shareName.getString("name", "default if empty");

//        Gson gson = new Gson();
//        String json = this.getSharedPreferences("name", MODE_PRIVATE).getString("name", "");
//        nameUser = gson.fromJson(json, Clinician.class);
        clinician = (Clinician) getIntent().getParcelableExtra("Clinician");
        nameUser = clinician;
        rvPatient = findViewById(R.id.rvPatient);
        fabAddPatient = findViewById(R.id.fabAddPatient);
        readPatientsFromFirebase();
        fabAddPatient.setOnClickListener(l -> {
            Dialog dialogAddPatient = new Dialog(this);

            dialogAddPatient.setContentView(R.layout.add_update_patient);
            Button btnAddUpdatePatient = dialogAddPatient.findViewById(R.id.btnAddUpdatePatient);
            Spinner spGenderPatient = dialogAddPatient.findViewById(R.id.spGenderPatient);
            EditText etDateBirthPatient = dialogAddPatient.findViewById(R.id.etDateBirthPatient);
            EditText etHourMeeting = dialogAddPatient.findViewById(R.id.etHourMeeting);
            EditText etDateMeeting = dialogAddPatient.findViewById(R.id.etDateMeeting);
//            rvPatient.setLayoutManager(new LinearLayoutManager(this));
//            PatientsAdapter = new ClinicianListPatientsAdapter(this, Patients, nameUser);
//            rvPatient.setAdapter(PatientsAdapter);
            spGenderPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    gender = type[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter adType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
            adType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGenderPatient.setAdapter(adType);

            etHourMeeting.setOnClickListener(k ->{
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = String.valueOf(selectedHour);

                        if (selectedHour<10){
                            hour = "0"+ String.valueOf(selectedHour);
                        }
                        minute = String.valueOf(selectedMinute);

                        if (selectedMinute<10){
                            minute = "0"+ String.valueOf(selectedMinute);
                        }

                        hourMeeting = hour + ":" + minute;

                        etHourMeeting.setText(hourMeeting);

                    }
                };
                int style = AlertDialog.THEME_HOLO_LIGHT;
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener,Integer.parseInt(hour),Integer.parseInt(minute),true);
                timePickerDialog.setTitle("select hour meeting");
                timePickerDialog.show();
            });

            etDateMeeting.setOnClickListener(k -> {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    // set day of month , month and year value in the edit text
                    dateMeeting = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    etDateMeeting.setText(dateMeeting);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
            etDateBirthPatient.setOnClickListener(k -> {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    // set day of month , month and year value in the edit text
                    dateBirthPatient = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    etDateBirthPatient.setText(dateBirthPatient);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
            btnAddUpdatePatient.setOnClickListener(h -> {
                String id = "";
                String numberPhone = "";
                String fullName = "";
                if (gender.equals("סוג")) {
                    Toast.makeText(this, "please enter gender patient", Toast.LENGTH_SHORT).show();
                }else if (dateBirthPatient.equals("")) {
                    Toast.makeText(this, "please choose date Patient", Toast.LENGTH_SHORT).show();
                }else if (dateMeeting.equals("")) {
                    Toast.makeText(this, "please choose date meeting", Toast.LENGTH_SHORT).show();
                }else if (hourMeeting.equals("")) {
                    Toast.makeText(this, "please choose hour meeting", Toast.LENGTH_SHORT).show();
                }else{

                    Patient Patient1 = new Patient(dateBirthPatient, gender,dateMeeting,hourMeeting,nameUser.getName(),"",nameUser.getClinicName());
                    readIdPatient(Patient1);
                    loadingdialog.startLoadingdialog();
//                    addPatientToFirebase(Patient1, id);
                    dialogAddPatient.dismiss();
                }



            });
            dialogAddPatient.show();
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume","onResume");

//        if (PatientsAdapter != null){
//            PatientsAdapter.clear();
//
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
//        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        // getting search view of our item.
//        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Patient> filteredlist = new ArrayList<Patient>();

        // running a for loop to compare elements.
        for (Patient item : Patients) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getIdentificationNumber().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            PatientsAdapter.filterList(filteredlist);
        }
    }
    private void readPatientsFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String dateBirth = String.valueOf(document.getData().get("dateBirth"));
                                String gender = String.valueOf(document.getData().get("gender"));
                                String dateMeeting = String.valueOf(document.getData().get("dateMeeting"));
                                String hourMeeting = String.valueOf(document.getData().get("hourMeeting"));

                                String prescribingClinician = String.valueOf(document.getData().get("prescribingClinician"));
                                String IdentificationNumber = String.valueOf(document.getData().get("IdentificationNumber"));
                                String clinicName = String.valueOf(document.getData().get("clinicName"));
                                System.out.println("clinicName"+clinicName);
                                Patient Patient = new Patient(dateBirth,gender,dateMeeting,hourMeeting,prescribingClinician,IdentificationNumber,clinicName);
                                Patients.add(Patient);
                            }
                            rvPatient.setLayoutManager(new LinearLayoutManager(context));
                            PatientsAdapter = new ClinicianListPatientsAdapter(context, Patients,nameUser);
                            rvPatient.setAdapter(PatientsAdapter);
                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});
    }

    private void addPatientToFirebase(Patient Patient1, Long id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> Patient = new HashMap<>();
        Patient.put("dateBirth", Patient1.getDateBirth());
        Patient.put("gender", Patient1.getGender());
        Patient.put("dateMeeting", Patient1.getDateMeeting());
        Patient.put("hourMeeting", Patient1.getHourMeeting());
        Patient.put("prescribingClinician", nameUser.getName());
        Patient.put("clinicName", nameUser.getClinicName());


        Activity activity = this;
//        Loading loadingdialog = new Loading(activity);
//        loadingdialog.startLoadingdialog();
        String date = Patient1.getDateBirth();
        date = date.replaceAll("/","");
//        String Patientname = Patient1.getId()+date;
        String identificationNumber = String.valueOf(nameUser.getId())+date+id;
        Patient.put("IdentificationNumber", identificationNumber);
        Patient1.setIdentificationNumber(identificationNumber);
        Patients.add(Patient1);
        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(identificationNumber)
                .set(Patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        updateIdClinician(id,Patient1);
//                        loadingdialog.dismissdialog();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });

    }

    private void readIdPatient(Patient patient1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("idPatient").document("idPatient")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Long id = (Long) (document.getData().get("id"));
                                addPatientToFirebase(patient1,id);

                            }
                        }
                    }
                });
    }

    private void updateIdClinician(Long id, Patient patient1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Long id2 = id + 1;
        db.collection("idPatient").document("idPatient")
                .update("id", id2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addPatientBestResultsFirebase(patient1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


    private void addPatientBestResultsFirebase(Patient Patient1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> Patient = new HashMap<>();
        Patient.put("date", "");
        Patient.put("image", "");


//"mouth_open"
//  "mouth_close"
//       "smile"
//        "lip_pursing"
//   "straight_tongue_out"
//"move_tongue_to_right"
//    "Move_tongue_to_left"
//    "lift_tongue_to_nose"
//  "down_tongue_to_chin"


        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                .collection("BestResults").document("mouth_open")
                .set(Patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                .collection("BestResults").document("mouth_close")
                                .set(Patient)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                .collection("BestResults").document("smile")
                                                .set(Patient)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                .collection("BestResults").document("lip_pursing")
                                                                .set(Patient)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                .collection("BestResults").document("straight_tongue_out")
                                                                                .set(Patient)
                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {

                                                                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                .collection("BestResults").document("move_tongue_to_right")
                                                                                                .set(Patient)
                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(Void aVoid) {

                                                                                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                .collection("BestResults").document("Move_tongue_to_left")
                                                                                                                .set(Patient)
                                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onSuccess(Void aVoid) {

                                                                                                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                                .collection("BestResults").document("lift_tongue_to_nose")
                                                                                                                                .set(Patient)
                                                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onSuccess(Void aVoid) {

                                                                                                                                        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                                                .collection("BestResults").document("down_tongue_to_chin")
                                                                                                                                                .set(Patient)
                                                                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onSuccess(Void aVoid) {

                                                                                                                                                        loadingdialog.dismissdialog();
                                                                                                                                                        rvPatient.setLayoutManager(new LinearLayoutManager(context));
                                                                                                                                                        PatientsAdapter = new ClinicianListPatientsAdapter(context, Patients, nameUser);
                                                                                                                                                        rvPatient.setAdapter(PatientsAdapter);
                                                                                                                                                    }
                                                                                                                                                })
                                                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                                        System.out.println("Error adding document");
                                                                                                                                                    }
                                                                                                                                                });
                                                                                                                                    }
                                                                                                                                })
                                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                        System.out.println("Error adding document");
                                                                                                                                    }
                                                                                                                                });
                                                                                                                    }
                                                                                                                })
                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                    @Override
                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                        System.out.println("Error adding document");
                                                                                                                    }
                                                                                                                });
                                                                                                    }
                                                                                                })
                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                    @Override
                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                        System.out.println("Error adding document");
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                })
                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        System.out.println("Error adding document");
                                                                                    }
                                                                                });
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        System.out.println("Error adding document");
                                                                    }
                                                                });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("Error adding document");
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("Error adding document");
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });

    }

}