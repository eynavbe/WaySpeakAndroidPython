package com.eynav.wayspeack;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicianListPatientsAdapter extends RecyclerView.Adapter<ClinicianListPatientsAdapter.ClinicianListPatientsAdapterHolder> {
    private List<Patient> Patients;
    Context context;
    String[] type = {"נקבה", "זכר"};
    String gender = "סוג";
    String dateBirthPatient = "";
    String  hourMeeting;
    String dateMeeting;
    Clinician nameUser;
    String  hour,minute;

    public ClinicianListPatientsAdapter(Context context, List<Patient> Patients, Clinician nameUser) {
        this.context = context;
        this.Patients = Patients;
        this.nameUser = nameUser;
    }

    @NonNull
    @Override
    public ClinicianListPatientsAdapter.ClinicianListPatientsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.clinician_list_patients_card_view, parent, false);
        return new ClinicianListPatientsAdapter.ClinicianListPatientsAdapterHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ClinicianListPatientsAdapter.ClinicianListPatientsAdapterHolder holder, @SuppressLint("RecyclerView") int position) {
        Patient Patient = Patients.get(position);
        holder.tvPatientName.setText(Patient.getIdentificationNumber());
        holder.tvPatientDate.setText(Patient.getDateBirth());
//        holder.tvPatientId.setText(Patient.getId());
        holder.Patient = Patient;
        holder.imUpdatePatient.setOnClickListener(l -> {
            Dialog dialogUpdateBirthPatient = new Dialog(context);
            dialogUpdateBirthPatient.setContentView(R.layout.add_update_patient);
            Button btnAddUpdateBirthPatient = dialogUpdateBirthPatient.findViewById(R.id.btnAddUpdatePatient);
            Spinner spGenderPatient = dialogUpdateBirthPatient.findViewById(R.id.spGenderPatient);
            EditText etDateBirthPatient = dialogUpdateBirthPatient.findViewById(R.id.etDateBirthPatient);
            EditText etHourMeeting = dialogUpdateBirthPatient.findViewById(R.id.etHourMeeting);
            EditText etDateMeeting = dialogUpdateBirthPatient.findViewById(R.id.etDateMeeting);

            btnAddUpdateBirthPatient.setText("Update");
            TextView tvTitleAddUpdateBirthPatient = dialogUpdateBirthPatient.findViewById(R.id.tvTitleAddUpdatePatient);
            tvTitleAddUpdateBirthPatient.setText("Update Patient");
            etDateBirthPatient.setText(Patient.getDateBirth());
            etHourMeeting.setText(String.valueOf(Patient.getHourMeeting()));
            etDateMeeting.setText(Patient.getDateMeeting());
            hourMeeting = Patient.getHourMeeting();
            dateMeeting = Patient.getDateMeeting();
            dateBirthPatient = Patient.getDateBirth();
            gender = Patient.getGender();
            spGenderPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    gender = type[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter adType = new ArrayAdapter(context, android.R.layout.simple_spinner_item, type);
            adType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGenderPatient.setAdapter(adType);


            for (int i = 0; i < type.length; i++) {
                if (type[i].equals(Patient.getIdentificationNumber())) {
                    spGenderPatient.setSelection(i);
                }
            }
            etHourMeeting.setOnClickListener(k ->{
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = String.valueOf(selectedHour);
                        if (selectedHour<10){
                            hour = "0"+ String.valueOf(selectedHour);
                        }
                        if (selectedMinute<10){
                            minute = "0"+ String.valueOf(selectedMinute);
                        }
                        minute = String.valueOf(selectedMinute);
                        hourMeeting = hour + ":" + minute;

                        etHourMeeting.setText(hourMeeting);

                    }
                };
                int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, style, onTimeSetListener,Integer.parseInt(hour),Integer.parseInt(minute),true);
                timePickerDialog.setTitle("select hour meeting");
                timePickerDialog.show();
            });

            etDateMeeting.setOnClickListener(k -> {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
                    // set day of month , month and year value in the edit text
                    dateBirthPatient = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    etDateBirthPatient.setText(dateBirthPatient);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
            btnAddUpdateBirthPatient.setOnClickListener(h -> {
                saveDeleteProduct(holder.Patient, 0, false);
                if (gender.equals("סוג")) {
                    Toast.makeText(context, "please enter gender patient", Toast.LENGTH_SHORT).show();
                }else if (dateBirthPatient.equals("")) {
                    Toast.makeText(context, "please choose date Patient", Toast.LENGTH_SHORT).show();
                }else if (dateMeeting.equals("")) {
                    Toast.makeText(context, "please choose date meeting", Toast.LENGTH_SHORT).show();
                }else if (hourMeeting.equals("")) {
                    Toast.makeText(context, "please choose hour meeting", Toast.LENGTH_SHORT).show();
                }else{
                    dateMeeting = etDateMeeting.getText().toString();
                    hourMeeting = etHourMeeting.getText().toString();
                    Patient Patient1 = new Patient(dateBirthPatient, gender,dateMeeting,hourMeeting,holder.Patient.getPrescribingClinician(),holder.Patient.getIdentificationNumber(),nameUser.getClinicName());
                    addPatientToFirebase(Patient1);
                    Patients.set(position, Patient1);
                    notifyItemChanged(position);
                    dialogUpdateBirthPatient.dismiss();
                }
                
            });
            dialogUpdateBirthPatient.show();
        });
        holder.imDeletePatient.setOnClickListener(l -> {
            AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                    .setTitle("Delete Patient")
                    .setMessage("אתה בטוח שאתה רוצה למחוק את המטופל?")
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveDeleteProduct(holder.Patient, position, true);


                        }
                    })
                    .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            builderDelete.show();
        });
    }
    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Patient> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        Patients = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    private void addPatientToFirebase(Patient Patient1) {
        System.out.println("addPatientToFirebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> Patient = new HashMap<>();
//        Patient.put("id", Patient1.getId());
//        Patient.put("numberPhone", Patient1.getNumberPhone());
//        Patient.put("dateBirth", Patient1.getDateBirth());
//        Patient.put("gender", Patient1.getGender());
//        Patient.put("name", Patient1.getName());
//        Patient.put("prescribingClinician", Patient1.getPrescribingClinician());

        Patient.put("dateBirth", Patient1.getDateBirth());
        Patient.put("gender", Patient1.getGender());
        Patient.put("dateMeeting", Patient1.getDateMeeting());
        Patient.put("hourMeeting", Patient1.getHourMeeting());
        Patient.put("prescribingClinician", Patient1.getPrescribingClinician());


//
//        String date = Patient1.getDateBirth();
//        date = date.replaceAll("/", "");
//        String Patientname = Patient1.getId() + date;
//
//        String identificationNumber = String.valueOf(nameUser.getId())+date;
        Patient.put("IdentificationNumber", Patient1.getIdentificationNumber());
        Patient.put("clinicName", Patient1.getClinicName());


        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                .set(Patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });

    }

    public void saveDeleteProduct(Patient Patient1, int position, boolean delete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String date = Patient1.getDateBirth();
        date = date.replaceAll("/", "");
//        String Patientname = Patient1.getId() + date;


//        Patient.put("IdentificationNumber", Patient1.getIdentificationNumber());


        db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (delete) {


                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                    .collection("BestResults").document("mouth_open")
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                    .collection("BestResults").document("mouth_close")
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                    .collection("BestResults").document("smile")
                                                                    .delete()
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                    .collection("BestResults").document("lip_pursing")
                                                                                    .delete()
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {

                                                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                    .collection("BestResults").document("straight_tongue_out")
                                                                                                    .delete()
                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void aVoid) {

                                                                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                    .collection("BestResults").document("move_tongue_to_right")
                                                                                                                    .delete()
                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Void aVoid) {

                                                                                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                                    .collection("BestResults").document("Move_tongue_to_left")
                                                                                                                                    .delete()
                                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onSuccess(Void aVoid) {

                                                                                                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                                                    .collection("BestResults").document("lift_tongue_to_nose")
                                                                                                                                                    .delete()
                                                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onSuccess(Void aVoid) {

                                                                                                                                                            db.collection("clinician").document(nameUser.getClinicName()).collection("Patients").document(Patient1.getIdentificationNumber())
                                                                                                                                                                    .collection("BestResults").document("down_tongue_to_chin")
                                                                                                                                                                    .delete()
                                                                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Void aVoid) {

                                                                                                                                                                            Patients.remove(position);

                                                                                                                                                                            notifyItemRemoved(position);


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
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure (@NonNull Exception e){
                                Log.e("error", e.getMessage());
                            }
                        });
                    }




    public void clear() {
        int size = Patients.size();
        Patients.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return Patients.size();
    }

    public class ClinicianListPatientsAdapterHolder extends RecyclerView.ViewHolder {
        Patient Patient;
//        TextView tvPatientName, tvPatientDate, tvPatientId;
        TextView tvPatientDate,tvPatientName;

        ImageView imUpdatePatient, imDeletePatient;
        LinearLayout cartPatient;
        
        public ClinicianListPatientsAdapterHolder(@NonNull View itemView) {
            super(itemView);
            cartPatient = itemView.findViewById(R.id.cartPatient);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvPatientDate = itemView.findViewById(R.id.tvPatientDate);
//            tvPatientId = itemView.findViewById(R.id.tvPatientId);
            imUpdatePatient = itemView.findViewById(R.id.imUpdatePatient);
            imDeletePatient = itemView.findViewById(R.id.imDeletePatient);
            itemView.setOnClickListener(l -> {
//                SharedPreferences shareHall = context.getSharedPreferences("hall", MODE_PRIVATE);
//
//                // save your string in SharedPreferences
//                shareHall.edit().putString("hall", hallName).commit();
                Intent clinicianOnePatient= new Intent(itemView.getContext(), MainActivity.class);
                System.out.println("+++++++");
                System.out.println(Patient);
                clinicianOnePatient.putExtra("Patient", Patient);
                clinicianOnePatient.putExtra("Clinician", nameUser);

                // get or create SharedPreferences
//                SharedPreferences shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);
//
//                // save your string in SharedPreferences
//                shareType.edit().putString("type", "Client").commit();
                itemView.getContext().startActivity(clinicianOnePatient);

//                Fragment myFragment = new AccountSummary(Patient, "Patient");
//                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
            });
        }
    }
}
