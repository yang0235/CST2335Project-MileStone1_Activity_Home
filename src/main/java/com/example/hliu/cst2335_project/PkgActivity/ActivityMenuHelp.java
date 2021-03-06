package com.example.hliu.cst2335_project.PkgActivity;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.example.hliu.cst2335_project.R;

import java.util.Locale;


public class ActivityMenuHelp extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_help);
        this.setFinishOnTouchOutside(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public void activity_help_dialog_ok(View view) {
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_menu_help, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.section_label);
            ImageView help_progress = (ImageView) rootView.findViewById(R.id.help_progress);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    if (Locale.getDefault().getLanguage().equals("zh")) {
                        imageView.setImageResource(R.drawable.activity_help_p1_zh);
                    }else {
                        imageView.setImageResource(R.drawable.activity_help_p1);

                    }
                    help_progress.setImageResource(R.drawable.help_tab1);
                    break;
                case 2:
                    if (Locale.getDefault().getLanguage().equals("zh")) {
                        imageView.setImageResource(R.drawable.activity_help_p2_zh);
                    }else{
                        imageView.setImageResource(R.drawable.activity_help_p2);
                    }
                    help_progress.setImageResource(R.drawable.help_tab2);
                    break;
                case 3:
                    if (Locale.getDefault().getLanguage().equals("zh")) {
                        imageView.setImageResource(R.drawable.activity_help_p3_zh);
                    }else{
                        imageView.setImageResource(R.drawable.activity_help_p3);
                    }
                    help_progress.setImageResource(R.drawable.help_tab3);
                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
