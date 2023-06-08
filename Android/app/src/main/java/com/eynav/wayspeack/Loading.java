package com.eynav.wayspeack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.eynav.wayspeack.R;

public class Loading {
    Activity activity;
    AlertDialog dialog;

    public Loading(Activity myActivity) {
        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    // dismiss method
    public void dismissdialog() {
        dialog.dismiss();
    }
}
