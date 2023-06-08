package com.eynav.wayspeack.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.TypeExercise;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;
    Patient patient;
    Exercise exercise;
    TypeExercise info;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Patient patient, Exercise exercise, TypeExercise info) {
        super(fm);
        mContext = context;
        this.patient = patient;
        this.exercise = exercise;
        this.info = info;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1, patient, exercise,info,mContext );
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}