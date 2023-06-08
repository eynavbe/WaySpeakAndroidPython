package com.eynav.wayspeack.ui.dashboard;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.eynav.wayspeack.Clinician;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    Patient patient;
    Clinician clinician;
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects, Patient patient, Clinician clinician) {
        super(context, resource, objects);
        this.patient = patient;
        this.clinician = clinician;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

//        ImageView photoImageView = convertView.findViewById(R.id.photoImageView);
        TextView messageTextView =  convertView.findViewById(R.id.messageTextView);
        TextView authorTextView =  convertView.findViewById(R.id.nameTextView);
        TextView dateTextView =  convertView.findViewById(R.id.dateTextView);
        CardView cvMessage = convertView.findViewById(R.id.cvMessage);
        FriendlyMessage message = getItem(position);

//        boolean isPhoto = message.getPhotoUrl() != null;
//        if (isPhoto) {
//            messageTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
//            Glide.with(photoImageView.getContext())
//                    .load(message.getPhotoUrl())
//                    .into(photoImageView);
//        } else {
            messageTextView.setVisibility(View.VISIBLE);
//            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        cvMessage.setOnClickListener(l ->{
            Fragment myFragment = new AddingDiagnosticMessage(patient, clinician, false, message);
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

        });
//        }
        authorTextView.setText(message.getName());
        dateTextView.setText(message.getDateAdd());
        return convertView;
    }

}
