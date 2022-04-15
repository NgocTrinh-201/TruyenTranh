package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ak.doctruyenchu.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new TruyenDaPublic();
        switch (position){
            case 0:
                fragment = new TruyenDaPublic();
                break;
            case 1:
                fragment = new TruyenChuaPublic();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position>0? "Chưa public" : "Đã public");
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}